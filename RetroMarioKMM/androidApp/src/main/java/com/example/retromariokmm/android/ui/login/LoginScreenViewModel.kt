package com.example.retromariokmm.android.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.android.ui.login.LoginState.Idle
import com.example.retromariokmm.domain.usecases.login.LoginUseCase
import com.example.retromariokmm.domain.usecases.retros.AddMeAndConnectToRetroUseCase
import com.example.retromariokmm.domain.usecases.retros.AddUserToRetroRetroUseCase
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val addMeAndConnectToRetroUseCase: AddMeAndConnectToRetroUseCase
) : ViewModel() {

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(Idle)
    val loginState = _loginState.asStateFlow()

    private val _loginCredentials: MutableStateFlow<LoginCredentials> = MutableStateFlow(LoginCredentials("", ""))
    val loginCredentials = _loginCredentials.asStateFlow()

    fun onLogin() {
        val email = loginCredentials.value.email
        val password = loginCredentials.value.password
        val retroId = loginCredentials.value.retroId
        if (email.isNotBlank() && password.isNotBlank()) {
            if (retroId != null) {
                viewModelScope.launch {
                    val result = loginUseCase.invoke(email, password)
                    if (result is Success) {
                        addMeAndConnectToRetroUseCase.invoke(retroId).collect { addUserResult ->
                            _loginState.value = when (addUserResult) {
                                is Error -> LoginState.Error(addUserResult.msg)
                                is Loading -> LoginState.Loading
                                is Success -> LoginState.SuccessLoginAndAddUser
                            }
                        }
                    }
                }
            } else {
                viewModelScope.launch {
                    val result = loginUseCase.invoke(email, password)
                    _loginState.value = when (result) {
                        is Error -> LoginState.Error(result.msg)
                        is Loading -> LoginState.Loading
                        is Success -> LoginState.Success
                    }
                }
            }
        }
    }

    fun onRetroIdAvailable(retroId: String) {
        _loginCredentials.value = _loginCredentials.value.copy(retroId = retroId)
    }

    fun onEmailChange(email: String) {
        _loginCredentials.value = _loginCredentials.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _loginCredentials.value = _loginCredentials.value.copy(password = password)
    }

}

sealed interface LoginState {
    object Idle : LoginState
    object Loading : LoginState
    object Success : LoginState
    object SuccessLoginAndAddUser : LoginState
    data class Error(val msg: String) : LoginState
}

data class LoginCredentials(
    val email: String,
    val password: String,
    val retroId: String? = null
)