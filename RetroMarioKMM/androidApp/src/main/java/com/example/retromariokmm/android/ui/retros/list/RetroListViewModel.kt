package com.example.retromariokmm.android.ui.retros.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.usecases.retros.ConnectUserToRetroUseCase
import com.example.retromariokmm.domain.usecases.retros.GetAllRetrosUseCase
import com.example.retromariokmm.utils.*
import com.example.retromariokmm.utils.ActionState.NOT_STARTED
import com.example.retromariokmm.utils.ActionState.SUCCESS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RetroListViewModel @Inject constructor(
    private val getAllRetrosUseCase: GetAllRetrosUseCase,
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
    val description: String
)