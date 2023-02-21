package com.example.retromariokmm.android.ui.actions.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.models.UserAction
import com.example.retromariokmm.domain.usecases.actions.ActionListUseCase
import com.example.retromariokmm.domain.usecases.actions.UpdateActionActorListUseCase
import com.example.retromariokmm.domain.usecases.actions.UpdateActionCheckStateUseCase
import com.example.retromariokmm.domain.usecases.users.CurrentUserUseCase
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
class ActionsViewModel @Inject constructor(
    private val actionListUseCase: ActionListUseCase,
    private val currentUserUseCase: CurrentUserUseCase,
    private val updateActionCheckStateUseCase: UpdateActionCheckStateUseCase,
    private val updateActionActorListUseCase: UpdateActionActorListUseCase
) : ViewModel() {

    private val _actionsState: MutableStateFlow<Resource<List<ActionContainer>>> = MutableStateFlow(Loading())
    val actionsState = _actionsState.asStateFlow()

    init {
        fetchAction()
    }

    private fun fetchAction() {
        val userId = currentUserUseCase.invoke()
        if (userId is Success) {
            viewModelScope.launch {
                actionListUseCase.invoke().collect { resource ->
                    _actionsState.value = when (resource) {
                        is Error -> Error(resource.msg)
                        is Loading -> Loading()
                        is Success -> Success(resource.value.map {
                            ActionContainer(
                                it,
                                userId.value.uid == it.creatorId,
                                userId.value.uid
                            )
                        })
                    }
                }
            }
        } else {
            _actionsState.value = Error("no user Id available ")
        }
    }

    fun onCheck(actionId: String, isCheck: Boolean) {
        viewModelScope.launch {
            updateActionCheckStateUseCase.invoke(actionId, isCheck)
        }
    }

    fun onTakenAction(actionId: String, isTaken: Boolean) {
        viewModelScope.launch {
            updateActionActorListUseCase.invoke(actionId, isTaken)
        }
    }
}

data class ActionContainer(
    val userAction: UserAction,
    val isFromCurrentUser: Boolean = false,
    val currentUserId: String
) {
    val actors get() = userAction.actorList?.entries?.map { it.value }
    val currentActor get() = userAction.actorList?.contains(currentUserId) ?: false
}