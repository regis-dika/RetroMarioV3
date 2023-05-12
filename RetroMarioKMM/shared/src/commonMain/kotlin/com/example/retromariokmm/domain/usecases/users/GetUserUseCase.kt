package com.example.retromariokmm.domain.usecases.users

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

//TODO Add Interface
class GetUserUseCase(private val repository: FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo
    fun invoke() = flow {
        emitAll(repository.getRetroUsers().map {
            when (it) {
                is Error -> Error<RetroUser>(it.msg)
                is Loading -> Loading<RetroUser>()
                is Success -> Success(it.value.firstOrNull { it.uid == repository.currentUser?.uid } ?: RetroUser())
            }
        })
    }
}