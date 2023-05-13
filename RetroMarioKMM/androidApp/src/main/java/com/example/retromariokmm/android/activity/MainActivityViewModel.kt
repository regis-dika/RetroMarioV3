package com.example.retromariokmm.android.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.domain.usecases.logout.LogoutUseCase
import com.example.retromariokmm.domain.usecases.users.GetUserUseCase
import com.example.retromariokmm.domain.usecases.users.SetCurrentUserUseCase
import com.example.retromariokmm.utils.ActionState
import com.example.retromariokmm.utils.ActionState.*
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Resource
import com.example.retromariokmm.utils.Success
import com.example.retromariokmm.utils.preferences.KMMPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.PrimitiveIterator
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val preferences: KMMPreference,
    setCurrentUserUseCase: SetCurrentUserUseCase
) : ViewModel() {
    private val _userState: MutableStateFlow<UserLoginScreen> = MutableStateFlow(UserLoginScreen())
    val userState get() = _userState.asStateFlow()

    init {
        val prefUser = preferences.getObject("user", null)
        if (prefUser != null) {
            setCurrentUserUseCase.invoke(prefUser)
            _userState.value = UserLoginScreen(retroUser = prefUser)
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            getUserUseCase.invoke().collect {
                if (it is Success) {
                    _userState.value = _userState.value.copy(retroUser = it.value)
                    it.value?.let { it1 -> preferences.put("user", it1) }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke().collect {
                _userState.value = _userState.value.copy(
                    logoutAction = when (it) {
                        is Error -> ERROR
                        is Loading -> PENDING
                        is Success -> {
                            preferences.put("user",null)
                            SUCCESS
                        }
                    },
                    retroUser = null
                )
            }
        }
    }
}

data class UserLoginScreen(
    val retroUser: RetroUser? = null,
    val logoutAction: ActionState = NOT_STARTED,
)