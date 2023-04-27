package com.example.retromariokmm.android.ui.retros.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.usecases.retros.AddMeAndConnectToRetroUseCase
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
    private val addMeAndConnectToRetroUseCase: AddMeAndConnectToRetroUseCase
) : ViewModel() {

    private val _retroCreationState: MutableStateFlow<RetroCreationContainer> =
        MutableStateFlow(RetroCreationContainer())
    val retroCreationState = _retroCreationState.asStateFlow()

    fun createRetro() {
        val title = retroCreationState.value.sprintTitle
        val description = retroCreationState.value.sprintDescription
        if (title.isNotBlank() && description.isNotBlank()) {
            viewModelScope.launch {
                createRetroUseCase.invoke(title, description)
                    .collect {
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
    }

    fun onTitle(title: String) {
        _retroCreationState.value = _retroCreationState.value.copy(sprintTitle = title)
    }

    fun onDescription(description: String) {
        _retroCreationState.value = _retroCreationState.value.copy(sprintDescription = description)
    }

    fun addMeToThisRetro() {
        val retroId = retroCreationState.value.retroId?.value ?: ""
        viewModelScope.launch {
            addMeAndConnectToRetroUseCase.invoke(retroId).collect {
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
    val sprintTitle: String = "",
    val sprintDescription: String = "",
    val retroId: Resource<String>? = null,
    val addMeToTheRetroAction: ActionState = NOT_STARTED
) {
    val urlToShare get() = "$BASE_URL/$retroId"
}