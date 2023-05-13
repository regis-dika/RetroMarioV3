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
class SetCurrentUserUseCase(private val repository: FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo
    fun invoke(currentUser: RetroUser) {
        repository.setUserUser(currentUser)
    }
}