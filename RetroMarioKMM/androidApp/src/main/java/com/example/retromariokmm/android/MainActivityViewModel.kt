package com.example.retromariokmm.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.domain.usecases.users.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {
    private val _userState: MutableStateFlow<RetroUser> = MutableStateFlow(RetroUser())
    val userState get() = _userState.asStateFlow()

    init {
        viewModelScope.launch {
            updateUserUseCase.invoke().collect {
                _userState.value =
                    it ?: RetroUser()
            }
        }
    }
}