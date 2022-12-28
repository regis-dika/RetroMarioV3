package com.example.retromariokmm.android.ui.comments.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.android.ui.components.FeelingsState
import com.example.retromariokmm.android.ui.components.FeelingsState.NOT_FEELINGS
import com.example.retromariokmm.domain.models.Feelings
import com.example.retromariokmm.domain.models.UserComment
import com.example.retromariokmm.domain.usecases.comments.StarCommentsListUseCase
import com.example.retromariokmm.domain.usecases.comments.UpdateLikeCommentUseCase
import com.example.retromariokmm.domain.usecases.users.CurrentUserUseCase
import com.example.retromariokmm.utils.*
import com.example.retromariokmm.utils.ActionState.NOT_STARTED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarCommentsViewModel @Inject constructor(
    private val starCommentsListUseCase: StarCommentsListUseCase,
    private val currentUserUseCase: CurrentUserUseCase,
    private val updateLikeCommentUseCase: UpdateLikeCommentUseCase
) : ViewModel() {
    private val _commentsState: MutableStateFlow<CommentsScreen> = MutableStateFlow(CommentsScreen())
    val commentsState = _commentsState.asStateFlow()

    init {
        fetchCommentsList()
    }

    private fun fetchCommentsList() {
        viewModelScope.launch {
            val currentUserId = currentUserUseCase.invoke()
            if (currentUserId is Success) {
                starCommentsListUseCase.invoke().collect { resource ->
                    _commentsState.value = _commentsState.value.copy(
                        comments = when (resource) {
                            is Error -> Error(resource.msg)
                            is Loading -> Loading()
                            is Success -> Success(resource.value.map {
                                CommentContainer(
                                    userComment = it,
                                    isFromCurrentUser = it.authorId == currentUserId.value.uid,
                                    feelingsFromCurrentUser = it.feelings?.map { it }
                                        ?.first { it.key == currentUserId.value.uid }?.value?.toFeelingState()
                                        ?: NOT_FEELINGS

                                )
                            })
                        }
                    )
                }
            } else {
                _commentsState.value = _commentsState.value.copy(comments = Error("no user Id available "))
            }
        }
    }

    fun updateLikeComment(commentId: String, isLiked: Boolean?) {
        viewModelScope.launch {
            updateLikeCommentUseCase.invoke(commentId, isLiked)
        }
    }
}

data class CommentsScreen(
    val comments: Resource<List<CommentContainer>> = Loading(),
    val createNoteAction: ActionState = NOT_STARTED
)

data class CommentContainer(
    val userComment: UserComment,
    val isFromCurrentUser: Boolean = false,
    val feelingsFromCurrentUser: FeelingsState = FeelingsState.NOT_FEELINGS
) {
    val nbLikes get() = userComment.feelings?.filter { it.value.state == 1L }?.map { it.value }
    val nbDisLikes get() = userComment.feelings?.filter { it.value.state == -1L }?.map { it.value }
}

fun Feelings.toFeelingState(): FeelingsState {
    return when (this.state) {
        1L -> FeelingsState.LIKE
        -1L -> FeelingsState.DISLIKE
        else -> FeelingsState.NOT_FEELINGS
    }
}