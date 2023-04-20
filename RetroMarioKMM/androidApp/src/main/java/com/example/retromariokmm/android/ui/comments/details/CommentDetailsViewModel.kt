package com.example.retromariokmm.android.ui.comments.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.usecases.comments.CreateCommentUseCase
import com.example.retromariokmm.domain.usecases.comments.GetCommentByIdUseCase
import com.example.retromariokmm.domain.usecases.comments.UpdateStarCommentUseCase
import com.example.retromariokmm.utils.ActionState
import com.example.retromariokmm.utils.ActionState.*
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCommentByIdUseCase: GetCommentByIdUseCase,
    private val createCommentUseCase: CreateCommentUseCase,
    private val updateStarCommentUseCase: UpdateStarCommentUseCase
) : ViewModel() {

    private val commentDescription = savedStateHandle.getStateFlow("commentDescription", "")
    private val _saveAction = MutableStateFlow(ActionState.NOT_STARTED)

    val state = combine(commentDescription, _saveAction) { description, save ->
        CommentDetailsState(description, save)
    }

    private var existingCommentId: String? = null
    private lateinit var commentPath: String

    init {
        savedStateHandle.get<String>("path")?.let {
            if (it.isBlank()) {
                return@let
            }
            commentPath = it
        }
        savedStateHandle.get<String>("commentId")?.let { s ->
            if (s.isBlank()) {
                return@let
            }
            existingCommentId = s
            fetchComment()

        }
    }

    private fun fetchComment() {
        viewModelScope.launch {
            getCommentByIdUseCase.invoke(commentPath, existingCommentId!!).collect {
                when (it) {
                    is Error -> {}
                    is Loading -> {}
                    is Success -> savedStateHandle["commentDescription"] = it.value.description
                }
            }
        }
    }

    fun onChangeDescription(updatedDescription: String) {
        savedStateHandle["commentDescription"] = updatedDescription
    }

    fun saveComment() {
        val commentId = existingCommentId
        viewModelScope.launch {
            if (commentId != null) {
                updateStarCommentUseCase.invoke(commentPath, commentId, commentDescription.value).collect {
                    _saveAction.value = when (it) {
                        is Error -> ERROR
                        is Loading -> PENDING
                        is Success -> SUCCESS
                    }
                }
            } else {
                createCommentUseCase.invoke(commentPath, commentDescription.value).collect {
                    _saveAction.value = when (it) {
                        is Error -> ERROR
                        is Loading -> PENDING
                        is Success -> SUCCESS
                    }
                }
            }
        }
    }
}

data class CommentDetailsState(
    val description: String = "",
    val saveActionState: ActionState = NOT_STARTED
)