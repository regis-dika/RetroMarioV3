package com.example.retromariokmm.android.ui.comments.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.models.UserComment
import com.example.retromariokmm.domain.usecases.comments.CreateStarCommentUseCase
import com.example.retromariokmm.domain.usecases.comments.StarCommentsListUseCase
import com.example.retromariokmm.domain.usecases.comments.UpdateStarCommentUseCase
import com.example.retromariokmm.domain.usecases.users.CurrentUserUseCase
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
    private val currentUserUseCase: CurrentUserUseCase
) : ViewModel() {
    private val _commentsState: MutableStateFlow<CommentsScreen> = MutableStateFlow(CommentsScreen())
    val commentsState = _commentsState.asStateFlow()

    init {
        fetchCommentsList()
    }

    private fun fetchCommentsList() {
        viewModelScope.launch {
            val currentUserId = currentUserUseCase.invoke()
            if(currentUserId is Success) {
                starCommentsListUseCase.invoke().collect { resource ->
                    _commentsState.value = _commentsState.value.copy(
                        comments = when (resource) {
                            is Error -> Error(resource.msg)
                            is Loading -> Loading()
                            is Success -> Success(resource.value.map {
                                CommentContainer(
                                    it,
                                    isFromCurrentUser = it.authorId == currentUserId.value.uid
                                )
                            })
                        }
                    )
                }
            }else{
                _commentsState.value = _commentsState.value.copy(comments = Error("no user Id available "))
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