package com.example.retromariokmm.android.ui.retros.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retromariokmm.domain.usecases.retros.GetAllRetrosUseCase
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
class RetroListViewModel @Inject constructor(
    private val getAllRetrosUseCase: GetAllRetrosUseCase
) : ViewModel() {

    private val _retrosState: MutableStateFlow<Resource<List<RetroContainer>>> = MutableStateFlow(Loading())
    val retrosState = _retrosState.asStateFlow()

    init {
        fetchRetro()
    }

    private fun fetchRetro() {
        viewModelScope.launch {
            getAllRetrosUseCase.invoke().collect {
                _retrosState.value = when (it) {
                    is Error -> Error(it.msg)
                    is Loading -> Loading()
                    is Success -> Success(it.value.map { retro ->
                        RetroContainer(
                            retro.retroId,
                            retro.title,
                            retro.description
                        )
                    })
                }
            }
        }
    }
}

data class RetroContainer(
    val retroId: String,
    val title: String,
    val description: String
)