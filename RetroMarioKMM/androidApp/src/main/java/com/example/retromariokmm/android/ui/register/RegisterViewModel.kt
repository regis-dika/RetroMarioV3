package com.example.retromariokmm.android.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.android.ui.register.RegisterActionState.Idle
import com.example.retromariokmm.domain.usecases.login.LoginUseCase
import com.example.retromariokmm.domain.usecases.register.RegisterUseCase
import com.example.retromariokmm.domain.usecases.retros.AddMeAndConnectToRetroUseCase
import com.example.retromariokmm.utils.ActionState
import com.example.retromariokmm.utils.ActionState.*
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel@Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val addMeAndConnectToRetroUseCase: AddMeAndConnectToRetroUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    init {
        _state.value
    }
    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun onFirstNameChange(firstname: String) {
        _state.value = _state.value.copy(firstname = firstname)
    }

    fun onNameChange(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun onRetroId(retroId: String?) {
        _state.value = _state.value.copy(retroId = retroId)
    }

    fun saveFormAndLogin() {
        val email = state.value.email
        val password = state.value.password
        val firstname = state.value.firstname
        val name = state.value.name
        val retroId = state.value.retroId
        viewModelScope.launch {
            if (retroId != null) {
                registerUseCase.invoke(email, password, firstname, name).flatMapLatest {
                    when (it) {
                        is Error -> flowOf(RegisterActionState.Error(it.msg))
                        is Loading -> flowOf(RegisterActionState.Loading)
                        is Success -> addMeAndConnectToRetroUseCase.invoke(retroId).flatMapLatest { addRes ->
                            when (addRes) {
                                is Error -> flowOf(RegisterActionState.Error(addRes.msg))
                                is Loading -> flowOf(RegisterActionState.Loading)
                                is Success -> flowOf(RegisterActionState.SuccessLoginAndAddUser)
                            }
                        }
                    }
                }.collect {
                    _state.value = _state.value.copy(saveState = it)
                }
            } else {
                registerUseCase.invoke(state.value.email, state.value.password, state.value.firstname, state.value.name)
                    .collect {
                        _state.value = _state.value.copy(
                            saveState = when (it) {
                                is Error -> RegisterActionState.Error(it.msg)
                                is Loading -> RegisterActionState.Loading
                                is Success -> RegisterActionState.Success
                            }
                        )
                    }
            }
        }
    }

    fun onImagePath(path: String?) {
        _state.value = _state.value.copy(picturePath = path)
    }
}

sealed interface RegisterActionState {
    object Idle : RegisterActionState
    object Loading : RegisterActionState
    object Success : RegisterActionState
    object SuccessLoginAndAddUser : RegisterActionState
    data class Error(val msg: String) : RegisterActionState
}

data class RegisterState(
    val picturePath : String? = null,
    val email: String = "",
    val password: String = "",
    val firstname: String = "",
    val name: String = "",
    val retroId: String? = null,
    val saveState: RegisterActionState = Idle
)