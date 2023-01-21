package com.example.retromariokmm.android.ui.retros.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retromariokmm.domain.usecases.retros.AddUserToRetroRetroUseCase
import com.example.retromariokmm.domain.usecases.retros.CreateRetroUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class RetroCreationViewModel @Inject constructor(
    private val createRetroUseCase: CreateRetroUseCase,
    private val addUserToRetroRetroUseCase: AddUserToRetroRetroUseCase
) : ViewModel() {
    init {
        viewModelScope.launch {
            addUserToRetroRetroUseCase.invoke("ju7pawssHkYQiQrJiVtw").collect {
            }
        }
    }
}