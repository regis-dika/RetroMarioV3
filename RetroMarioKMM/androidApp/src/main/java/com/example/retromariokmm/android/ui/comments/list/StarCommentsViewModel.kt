package com.example.retromariokmm.android.ui.comments.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.models.UserComment
import com.example.retromariokmm.domain.usecases.comments.CreateStarCommentUseCase
import com.example.retromariokmm.domain.usecases.comments.StarCommentsListUseCase
import com.example.retromariokmm.domain.usecases.comments.UpdateStarCommentUseCase
import com.example.retromariokmm.utils.*
import com.example.retromariokmm.utils.ActionState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class StarCommentsViewModel @Inject constructor(
    private val starCommentsListUseCase: StarCommentsListUseCase,
    private val createStarCommentUseCase: CreateStarCommentUseCase,
    private val updateStarCommentUseCase: UpdateStarCommentUseCase
) : ViewModel() {
    private val _commentsState: MutableStateFlow<CommentsScreen> = MutableStateFlow(CommentsScreen())
    val commentsState = _commentsState.asStateFlow()

    init {
        fetchCommentsList()
    }

    private fun fetchCommentsList() {
        viewModelScope.launch {
            starCommentsListUseCase.invoke().collect { resource ->
                _commentsState.value = _commentsState.value.copy(
                    comments = when (resource) {
                        is com.example.retromariokmm.utils.Error -> com.example.retromariokmm.utils.Error(resource.msg)
                        is Loading -> Loading()
                        is Success -> Success(resource.value.map { CommentContainer(it) })
                    }
                )
            }
        }
    }

    fun createComment() {
        viewModelScope.launch {
            createStarCommentUseCase.invoke(
                UserComment(
                    description = "BlablaBlaBla"
                )
            ).collect {
                _commentsState.value = _commentsState.value.copy(
                    createNoteAction = when (it) {
                        is Error -> ERROR
                        is Loading -> PENDING
                        is Success -> SUCCESS
                    }
                )
            }
        }
    }

    fun updateComment(commentId: String) {
        viewModelScope.launch {
            updateStarCommentUseCase.invoke(
                commentId, Random(1000).toString()
            ).collect {
                _commentsState.value = _commentsState.value.copy(
                    createNoteAction = when (it) {
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
    val createNoteAction: ActionState = NOT_STARTED
)

data class CommentContainer(
    val userComment: UserComment,
    val isFromCurrentUser: Boolean = false
)