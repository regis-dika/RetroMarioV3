package com.example.retromariokmm.domain.usecases.users

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

//TODO Add Interface
class UpdateUserUseCase(private val repository: FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo
    fun invoke() = flow {
        emitAll(repository.getRetroUsers().map {
            when (it) {
                is Error -> TODO()
                is Loading -> TODO()
                is Success -> it.value.firstOrNull { it.uid == repository.currentUser?.uid }
            }
        })
    }
}