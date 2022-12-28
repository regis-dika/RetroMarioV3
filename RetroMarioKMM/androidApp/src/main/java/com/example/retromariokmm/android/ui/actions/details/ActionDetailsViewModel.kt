package com.example.retromariokmm.android.ui.actions.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.usecases.actions.CreateActionUseCase
import com.example.retromariokmm.utils.ActionState
import com.example.retromariokmm.utils.ActionState.*
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActionDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val createActionUseCase: CreateActionUseCase

) : ViewModel() {

    private val actionTitle = savedStateHandle.getStateFlow("actionTitle", "")
    private val actionDescription = savedStateHandle.getStateFlow("actionDescription", "")
    private val _saveAction = MutableStateFlow(ActionState.NOT_STARTED)

    val state = combine(actionTitle,actionDescription, _saveAction) { title,description, save ->
        ActionDetailsState(title, description, save)
    }

    private var existingActionId: String? = null

    init {
        savedStateHandle.get<String>("actionId")?.let {
            if(it.isBlank()){
                return@let
            }
            existingActionId = it
            fetchComment()
        }
    }

    private fun fetchComment() {
        viewModelScope.launch {
            /*getCommentByIdUseCase.invoke(existingActionId!!).collect {
                when (it) {
                    is Error -> {}
                    is Loading -> {}
                    is Success -> savedStateHandle["commentDescription"] = it.value.description
                }
            }*/
        }
    }

    fun onChangeTitle(updatedTitle: String) {
        savedStateHandle["actionTitle"] = updatedTitle
    }
    fun onChangeDescription(updatedDescription: String) {
        savedStateHandle["actionDescription"] = updatedDescription
    }

    fun saveAction() {
        val commentId = existingActionId
        viewModelScope.launch {
            if (commentId != null) {
                /*updateStarCommentUseCase.invoke(commentId, commentDescription.value).collect {
                    _saveAction.value = when (it) {
                        is Error -> ActionState.ERROR
                        is Loading -> PENDING
                        is Success -> SUCCESS
                    }
                }*/
            } else {
                createActionUseCase.invoke(actionTitle.value,actionDescription.value).collect {
                    _saveAction.value = when (it) {
                        is Error -> ActionState.ERROR
                        is Loading -> PENDING
                        is Success -> SUCCESS
                    }
                }
            }
        }
    }
}

data class ActionDetailsState(
    val title: String = "",
    val description: String = "",
    val saveActionState: ActionState = NOT_STARTED
)