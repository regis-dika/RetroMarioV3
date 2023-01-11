package com.example.retromariokmm.android.ui.lifeanddifficulty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.usecases.users.CurrentUserUseCase
import com.example.retromariokmm.domain.usecases.users.SetLifeDifficultyUseCase
import com.example.retromariokmm.domain.usecases.users.UserListUseCase
import com.example.retromariokmm.utils.*
import com.example.retromariokmm.utils.ActionState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LifeAndDifficultyViewModel @Inject constructor(
    private val userListUseCase: UserListUseCase,
    private val setLifeDifficultyUseCase: SetLifeDifficultyUseCase,
    private val currentUserUseCase: CurrentUserUseCase
) : ViewModel() {

    private val _usersState = MutableStateFlow(UsersStateScreen(Loading(), NOT_STARTED))
    val usersState = _usersState.asStateFlow()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        val currentUser = currentUserUseCase.invoke()
        if (currentUser is Success) {
            viewModelScope.launch {
                userListUseCase.invoke().collect {
                    _usersState.value = _usersState.value.copy(
                        userContainerList = when (it) {
                            is Success -> Success(it.value.map { user ->
                                UserContainer(
                                    user.uid,
                                    user.firstName ?: "error name",
                                    user.name ?: "error name",
                                    user.bitmap,
                                    user.life,
                                    user.difficulty,
                                    user.uid == currentUser.value.uid
                                )
                            }.sortedBy { it.life })
                            is Loading -> (Loading())
                            is Error -> Error(it.msg)
                        }
                    )
                }
            }
        }
    }

    fun setLifeAndDifficulty(life: Int,difficulty: Int) {
        viewModelScope.launch {
            setLifeDifficultyUseCase.invoke(life, difficulty).collect {
                _usersState.value = _usersState.value.copy(
                    lifeAndDifficultyAction = when (it) {
                        is Error -> ERROR
                        is Loading -> PENDING
                        is Success -> SUCCESS
                    }
                )
            }
        }
    }
}

data class UsersStateScreen(
    val userContainerList: Resource<List<UserContainer>>,
    val lifeAndDifficultyAction: ActionState
)

data class UserContainer(
    val uid: String,
    val firstName: String,
    val lastName: String,
    val picture: String,
    val life: Int,
    val difficulty: Int,
    val isCurrentUser: Boolean
)