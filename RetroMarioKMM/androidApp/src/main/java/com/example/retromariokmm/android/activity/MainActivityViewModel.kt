package com.example.retromariokmm.android.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.domain.usecases.users.GetUserUseCase
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Resource
import com.example.retromariokmm.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {
    private val _userState: MutableStateFlow<RetroUser> = MutableStateFlow(RetroUser())
    val userState get() = _userState.asStateFlow()

    fun getCurrentUser() {
        viewModelScope.launch {
            getUserUseCase.invoke().collect {
                if (it is Success) {
                    _userState.value = it.value
                }
            }
        }
    }
}