package com.example.retromariokmm.android.ui.retros.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.models.UserAction
import com.example.retromariokmm.domain.usecases.actions.ActionListFromRetroUseCase
import com.example.retromariokmm.domain.usecases.retros.ConnectUserToRetroUseCase
import com.example.retromariokmm.domain.usecases.retros.GetAllRetrosUseCase
import com.example.retromariokmm.utils.*
import com.example.retromariokmm.utils.ActionState.NOT_STARTED
import com.example.retromariokmm.utils.ActionState.SUCCESS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RetroListViewModel @Inject constructor(
    private val getAllRetrosUseCase: GetAllRetrosUseCase,
    private val actionListFromRetroUseCase: ActionListFromRetroUseCase,
    private val connectUserToRetroUseCase: ConnectUserToRetroUseCase
) : ViewModel() {

    private val _retrosState: MutableStateFlow<RetroListState> = MutableStateFlow(RetroListState())
    val retrosState = _retrosState.asStateFlow()

    init {
        fetchRetro()
    }

    private fun fetchRetro() {
        viewModelScope.launch {
            getAllRetrosUseCase.invoke().collect {
                _retrosState.value = _retrosState.value.copy(
                    list = when (it) {
                        is Error -> Error(it.msg)
                        is Loading -> Loading()
                        is Success -> Success(it.value.map { retro ->
                            RetroContainer(
                                retro.id,
                                retro.title,
                                retro.description
                            )
                        })
                    }
                )
            }
        }
    }

    fun getActionsFromRetro(retroId: String) {
        if (_retrosState.value.list is Success) {
            viewModelScope.launch {
                val list = _retrosState.value.list.value?.toMutableList()
                val formerRetro = _retrosState.value.list.value?.first { it.retroId == retroId }
                actionListFromRetroUseCase.invoke(retroId).collect {
                    when (it) {
                        is Error -> _retrosState.value = _retrosState.value.copy(list = Error(it.msg))
                        is Loading -> _retrosState.value = _retrosState.value.copy(list = Loading())
                        is Success -> {
                            val newRetro = formerRetro?.copy(actionsList = it.value)
                            if (formerRetro != null && newRetro != null) {
                                if (list != null) {
                                    Collections.replaceAll(list, formerRetro, newRetro)
                                    _retrosState.value = _retrosState.value.copy(list = Success(list.toList()))
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    fun connectToRetro(retroId: String) {
        viewModelScope.launch {
            connectUserToRetroUseCase.invoke(retroId)
            _retrosState.value = _retrosState.value.copy(connectAction = SUCCESS)
        }
    }
}

data class RetroListState(
    val list: Resource<List<RetroContainer>> = Loading(),
    val connectAction: ActionState = NOT_STARTED
)

data class RetroContainer(
    val retroId: String,
    val title: String,
    val description: String,
    val actionsList: List<UserAction> = emptyList()
)