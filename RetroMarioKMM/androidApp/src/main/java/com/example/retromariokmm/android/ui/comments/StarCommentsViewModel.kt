package com.example.retromariokmm.android.ui.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.models.UserComment
import com.example.retromariokmm.domain.usecases.comments.SetStarCommentUseCase
import com.example.retromariokmm.domain.usecases.comments.StarCommentsListUseCase
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Resource
import com.example.retromariokmm.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarCommentsViewModel @Inject constructor(
    private val starCommentsListUseCase: StarCommentsListUseCase,
    private val setStarCommentUseCase: SetStarCommentUseCase
) : ViewModel() {
    private val _commentsState: MutableStateFlow<Resource<List<CommentContainer>>> = MutableStateFlow(Loading())
    val commentsState = _commentsState.asStateFlow()


    init {
        fetchCommentsList()
    }
    private fun fetchCommentsList() {
        viewModelScope.launch {
            starCommentsListUseCase.invoke().collect { resource ->
                _commentsState.value = when (resource) {
                    is com.example.retromariokmm.utils.Error -> com.example.retromariokmm.utils.Error(resource.msg)
                    is Loading -> Loading()
                    is Success -> Success(resource.value.map { CommentContainer(it) })
                }
            }
        }
    }

}

data class CommentContainer(
    val userComment: UserComment,
    val isFromCurrentUser: Boolean = false
)