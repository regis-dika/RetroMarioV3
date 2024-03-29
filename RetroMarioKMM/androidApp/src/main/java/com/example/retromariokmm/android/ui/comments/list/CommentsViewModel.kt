package com.example.retromariokmm.android.ui.comments.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.android.ui.components.FeelingsState
import com.example.retromariokmm.android.ui.components.FeelingsState.NOT_FEELINGS
import com.example.retromariokmm.domain.models.Feelings
import com.example.retromariokmm.domain.models.UserComment
import com.example.retromariokmm.domain.usecases.comments.CommentsListUseCase
import com.example.retromariokmm.domain.usecases.comments.CreateCommentUseCase
import com.example.retromariokmm.domain.usecases.comments.UpdateLikeCommentUseCase
import com.example.retromariokmm.domain.usecases.comments.UpdateCommentUseCase
import com.example.retromariokmm.domain.usecases.users.CurrentUserUseCase
import com.example.retromariokmm.utils.*
import com.example.retromariokmm.utils.ActionState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val commentsListUseCase: CommentsListUseCase,
    private val createCommentUseCase: CreateCommentUseCase,
    private val currentUserUseCase: CurrentUserUseCase,
    private val updateLikeCommentUseCase: UpdateLikeCommentUseCase,
    private val updateCommentUseCase: UpdateCommentUseCase
) : ViewModel() {
    private val commentDescription = savedStateHandle.getStateFlow("commentDescription", "")
    private val _saveAction = MutableStateFlow(NOT_STARTED)

    val newCommentState = combine(commentDescription, _saveAction) { description, save ->
        NewCommentState(description, save)
    }
    private val _commentsState: MutableStateFlow<CommentsScreen> = MutableStateFlow(CommentsScreen())
    val commentsState = _commentsState.asStateFlow()

    private lateinit var path: String

    init {
        savedStateHandle.get<String>("path")?.let {
            if (it.isBlank()) {
                return@let
            }
            path = it
            fetchCommentsList()
        }
    }

    private fun fetchCommentsList() {
        viewModelScope.launch {
            val currentUserId = currentUserUseCase.invoke()
            if (currentUserId is Success) {
                commentsListUseCase.invoke(path).collect { resource ->
                    _commentsState.value = _commentsState.value.copy(
                        comments = when (resource) {
                            is Error<*> -> Error(resource.msg)
                            is Loading<*> -> Loading()
                            is Success -> Success(resource.value.map { userComment ->
                                CommentContainer(
                                    userComment = userComment,
                                    isFromCurrentUser = userComment.creatorId == currentUserId.value.uid,
                                    feelingsFromCurrentUser = userComment.feelings?.map { it }
                                        ?.firstOrNull() { it.key == currentUserId.value.uid }?.value?.toFeelingState()
                                        ?: NOT_FEELINGS

                                )
                            })
                            else -> Error("incomptaible state")
                        }
                    )
                }
            } else {
                _commentsState.value = _commentsState.value.copy(comments = Error("no user Id available "))
            }
        }
    }

    fun onCurrentCommentChange(updatedDescription: String) {
        savedStateHandle["commentDescription"] = updatedDescription
    }

    fun createComment() {
        viewModelScope.launch {
            createCommentUseCase.invoke(path, commentDescription.value).collect {
                _saveAction.value = when (it) {
                    is Error -> ERROR
                    is Loading -> PENDING
                    is Success -> {
                        onCurrentCommentChange("")
                        SUCCESS
                    }
                }
            }
        }
    }

    fun updateLikeComment(commentId: String, isLiked: Boolean?) {
        viewModelScope.launch {
            updateLikeCommentUseCase.invoke(path, commentId, isLiked).collect {
                _commentsState.value = _commentsState.value.copy(
                    comments = when (it) {
                        is Error -> Error("Error on lime change")
                        else -> _commentsState.value.comments
                    }
                )
            }
        }
    }

    fun onEditDescriptionChange(description: String) {
        _commentsState.value = _commentsState.value.copy(descriptionEditable = description)
    }

    fun editComment(commentId: String) {
        val editDescription = commentsState.value.descriptionEditable
        viewModelScope.launch {
            updateCommentUseCase.invoke(path, commentId, editDescription).collect {
                _commentsState.value = _commentsState.value.copy(
                    saveActionState = when (it) {
                        is Error -> ERROR
                        is Loading -> PENDING
                        is Success -> SUCCESS
                    }
                )
            }
        }
    }
}

data class CommentsScreen(
    val comments: Resource<List<CommentContainer>> = Loading(),
    val descriptionEditable: String = "",
    val saveActionState: ActionState = NOT_STARTED

)

data class NewCommentState(
    val description: String = "",
    val saveActionState: ActionState = NOT_STARTED
)

data class CommentContainer(
    val userComment: UserComment,
    val isFromCurrentUser: Boolean = false,
    val feelingsFromCurrentUser: FeelingsState = NOT_FEELINGS
) {
    val nbLikes get() = userComment.feelings?.filter { it.value.state == 1L }?.map { it.value }
    val nbDisLikes get() = userComment.feelings?.filter { it.value.state == -1L }?.map { it.value }
}

fun Feelings.toFeelingState(): FeelingsState {
    return when (this.state) {
        1L -> FeelingsState.LIKE
        -1L -> FeelingsState.DISLIKE
        else -> NOT_FEELINGS
    }
}