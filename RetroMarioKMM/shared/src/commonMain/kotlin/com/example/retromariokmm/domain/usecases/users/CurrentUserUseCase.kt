package com.example.retromariokmm.domain.usecases.users

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.utils.Resource
import com.example.retromariokmm.utils.Success
import kotlinx.coroutines.flow.Flow

//TODO Add Interface
class CurrentUserUseCase(private val repository: FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo
    fun invoke(): Resource<RetroUser> {
        val currentUser = repository.currentUser
        return if (currentUser == null) {
            com.example.retromariokmm.utils.Error("No user login")
        } else {
            Success(currentUser)
        }
    }
}