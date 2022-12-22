package com.example.retromariokmm.domain.usecases.login

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.utils.Resource

class LoginUseCase(private val repository : FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo

    suspend fun invoke(email: String, password: String): Resource<RetroUser> {
        return repository.signIn(email, password)
    }
}