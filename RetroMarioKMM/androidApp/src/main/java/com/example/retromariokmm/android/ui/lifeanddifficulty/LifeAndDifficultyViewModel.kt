package com.example.retromariokmm.android.ui.lifeanddifficulty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val setLifeDifficultyUseCase: SetLifeDifficultyUseCase
) : ViewModel() {

    private val _usersState = MutableStateFlow(UsersStateScreen(Loading(), -1, -1, NOT_STARTED))
    val usersState = _usersState.asStateFlow()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            userListUseCase.invoke().collect {
                _usersState.value = _usersState.value.copy(
                    userContainerList = when (it) {
                        is Success -> Success(it.value.map { user ->
                            UserContainer(
                                user.uid,
                                user.name,
                                user.bitmap,
                                user.life,
                                user.difficulty,
                                user.uid == ""
                            )
                        }.sortedBy { it.life })
                        is Loading -> (Loading())
                        is Error -> Error(it.msg)
                    }
                )
            }
        }
    }

    fun onLifeChange(life: Int) {
        _usersState.value = _usersState.value.copy(life = life)
    }

    fun onDifficultyChange(difficulty: Int) {
        _usersState.value = _usersState.value.copy(difficulty = difficulty)
    }

    fun setLifeAndDifficulty() {
        val currentLife = usersState.value.life
        val currentDifficulty = usersState.value.difficulty
        viewModelScope.launch {
            setLifeDifficultyUseCase.invoke(currentLife, currentDifficulty).collect {
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
    val life: Int,
    val difficulty: Int,
    val lifeAndDifficultyAction: ActionState
)

data class UserContainer(
    val uid: String,
    val firstName: String,
    val lastName: String,
    val life: Int,
    val difficulty: Int,
    val isCurrentUser: Boolean
)