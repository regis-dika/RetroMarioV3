package com.example.retromariokmm.android.ui.comments.board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.usecases.comments.CommentsListUseCase
import com.example.retromariokmm.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CommentsBoardViewModel @Inject constructor(
    commentsListUseCase: CommentsListUseCase
) : ViewModel() {

    val boardState : Flow<Resource<CommentBoardState>> = combine(
        commentsListUseCase.invoke(STAR_COMMENTS), commentsListUseCase.invoke(BOO_COMMENTS), commentsListUseCase.invoke(
            GOOMBA_COMMENTS
        ), commentsListUseCase.invoke(MUSHROOM_COMMENTS)
    ) { starRes, booRes, goombaRes, mushroomRes ->
        val star = when (starRes) {
            is Error<*> -> return@combine Error(starRes.msg)
            is Loading<*> -> return@combine Loading()
            is Success -> starRes.value.size
            else -> return@combine Error("incomptaible state")
        }
        val boo = when (booRes) {
            is Error<*> -> return@combine Error(booRes.msg)
            is Loading<*> -> return@combine Loading()
            is Success -> booRes.value.size
            else -> return@combine Error("incomptaible state")
        }
        val goomba = when (goombaRes) {
            is Error<*> -> return@combine Error(goombaRes.msg)
            is Loading<*> -> return@combine Loading()
            is Success -> goombaRes.value.size
            else -> return@combine Error("incomptaible state")
        }
        val mushroom = when (mushroomRes) {
            is Error<*> -> return@combine Error(mushroomRes.msg)
            is Loading<*> -> return@combine Loading()
            is Success -> mushroomRes.value.size
            else -> return@combine Error("incomptaible state")
        }
        Success(CommentBoardState(star, boo, goomba, mushroom))

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading())
}

data class CommentBoardState(
    val nbStarComments: Int = 0,
    val nbBooComments: Int = 0,
    val nbGoombaComments: Int = 0,
    val nbMushroomComments: Int = 0,
)