package com.example.retromariokmm.android.ui.retros.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.usecases.retros.AddUserToRetroRetroUseCase
import com.example.retromariokmm.domain.usecases.retros.CreateRetroUseCase
import com.example.retromariokmm.utils.*
import com.example.retromariokmm.utils.ActionState.*
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

    private val _retroCreationState: MutableStateFlow<RetroCreationContainer> =
        MutableStateFlow(RetroCreationContainer())
    val retroCreationState = _retroCreationState.asStateFlow()

    init {
        viewModelScope.launch {
            createRetroUseCase.invoke(Random.nextInt(8).toString(), "Blablabla").collect {
                _retroCreationState.value = _retroCreationState.value.copy(
                    retroId =
                    when (it) {
                        is Error -> Error(it.msg)
                        is Loading -> Loading()
                        is Success -> Success(it.value)
                    }
                )
            }
        }
    }

    fun addMeToThisRetro() {
        val retroId = retroCreationState.value.retroId.value ?: ""
        viewModelScope.launch {
            addUserToRetroRetroUseCase.invoke(retroId).collect {
                _retroCreationState.value = _retroCreationState.value.copy(
                    addMeToTheRetroAction = when (it) {
                        is Error -> ERROR
                        is Loading -> PENDING
                        is Success -> SUCCESS
                    }
                )
            }
        }
    }
}

data class RetroCreationContainer(
    val retroId: Resource<String> = Loading(),
    val addMeToTheRetroAction: ActionState = NOT_STARTED
) {
    val urlToShare get() = "$BASE_URL/$retroId"
}