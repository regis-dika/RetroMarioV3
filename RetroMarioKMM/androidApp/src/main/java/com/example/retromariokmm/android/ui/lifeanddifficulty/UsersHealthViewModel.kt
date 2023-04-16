package com.example.retromariokmm.android.ui.lifeanddifficulty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.usecases.users.CurrentUserUseCase
import com.example.retromariokmm.domain.usecases.users.UpdateDifficultyUseCase
import com.example.retromariokmm.domain.usecases.users.UpdateLifeUseCase
import com.example.retromariokmm.domain.usecases.users.UserListUseCase
import com.example.retromariokmm.utils.*
import com.example.retromariokmm.utils.ActionState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LifeAndDifficultyViewModel @Inject constructor(
    private val userListUseCase: UserListUseCase,
    private val updateLifeUseCase: UpdateLifeUseCase,
    private val updateDifficultyUseCase: UpdateDifficultyUseCase,
    private val currentUserUseCase: CurrentUserUseCase
) : ViewModel() {

    private val _usersState = MutableStateFlow(UsersStateScreen(Loading()))
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
                            }.sortedBy { it.isCurrentUser }.sortedBy { it.life })
                            is Loading -> (Loading())
                            is Error -> Error(it.msg)
                        },
                        currentLife = currentUser.value.life,
                        currentDifficulty = currentUser.value.difficulty
                    )
                }
            }
        }
    }

    fun setLife(life: Int) {
        _usersState.value = _usersState.value.copy(currentLife = life)
    }

    fun setDifficulty(difficulty: Int) {
        _usersState.value = _usersState.value.copy(currentDifficulty = difficulty)
    }

    fun saveHealth() {
        viewModelScope.launch {
            combine(
                updateLifeUseCase.invoke(usersState.value.currentLife),
                updateDifficultyUseCase.invoke(usersState.value.currentDifficulty)
            ) { l, d ->
                val life = when (l) {
                    is Error -> ERROR
                    is Loading -> PENDING
                    is Success -> SUCCESS
                }
                val difficulty = when (d) {
                    is Error -> ERROR
                    is Loading -> PENDING
                    is Success -> SUCCESS
                }
                Pair(life, difficulty)
            }.collect {
                _usersState.value = _usersState.value.copy(
                    lifeAndDifficultyAction = when {
                        it.first == SUCCESS && it.second == SUCCESS -> SUCCESS
                        it.first == PENDING || it.second == PENDING -> PENDING
                        it.first == ERROR || it.second == ERROR -> ERROR
                        else -> ERROR

                    }
                )
            }
        }
    }
}

data class UsersStateScreen(
    val userContainerList: Resource<List<UserContainer>>,
    val currentLife: Int = 0,
    val currentDifficulty: Int = 0,
    val lifeAndDifficultyAction: ActionState = NOT_STARTED,
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