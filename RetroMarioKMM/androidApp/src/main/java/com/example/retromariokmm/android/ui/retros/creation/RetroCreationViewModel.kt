package com.example.retromariokmm.android.ui.retros.creation

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.android.helper.generateSharingLink
import com.example.retromariokmm.domain.usecases.retros.AddUserToRetroRetroUseCase
import com.example.retromariokmm.domain.usecases.retros.CreateRetroUseCase
import com.example.retromariokmm.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class RetroCreationViewModel @Inject constructor(
    private val createRetroUseCase: CreateRetroUseCase,
    private val addUserToRetroRetroUseCase: AddUserToRetroRetroUseCase
) : ViewModel() {

    private val _retroCreationState: MutableStateFlow<Resource<RetroCreationContainer>> = MutableStateFlow(Loading())
    val retroCreationState = _retroCreationState.asStateFlow()

    init {
        viewModelScope.launch {
            createRetroUseCase.invoke(Random.nextInt(8).toString(), "Blablabla").collect {
                _retroCreationState.value = when (it) {
                    is Error -> Error(it.msg)
                    is Loading -> Loading()
                    is Success -> Success(RetroCreationContainer(it.value))
                }
            }
        }
    }
}

data class RetroCreationContainer(
    val retroId: String
){
    val urlToShare get() = "$BASE_URL/$retroId"
}