package com.example.retromariokmm.android.ui.lifeanddifficulty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.usecases.users.UserListUseCase
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Resource
import com.example.retromariokmm.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LifeAndDifficultyViewModel @Inject constructor(
    private val userListUseCase: UserListUseCase
) : ViewModel() {

    private val _usersState = MutableStateFlow(UsersStateScreen(Loading()))
    val usersState = _usersState.asStateFlow()

    init {
        fetchUsers()
    }

    private  fun fetchUsers() {
        viewModelScope.launch {
            userListUseCase.invoke().collect {
                _usersState.value = when (it) {
                    is Success -> UsersStateScreen(Success(it.value.map { user ->
                        UserContainer(
                            user.name,
                            user.bitmap,
                            user.life,
                            user.difficulty,
                            user.uid == ""
                        )
                    }))
                    is Loading -> UsersStateScreen((Loading()))
                    is Error -> UsersStateScreen(Error(it.msg))
                }
            }
        }
    }
}

data class UsersStateScreen(
    val userContainerList: Resource<List<UserContainer>>
)

data class UserContainer(
    val firstName: String,
    val lastName: String,
    val life: Int,
    val difficulty: Int,
    val isCurrentUser: Boolean
)