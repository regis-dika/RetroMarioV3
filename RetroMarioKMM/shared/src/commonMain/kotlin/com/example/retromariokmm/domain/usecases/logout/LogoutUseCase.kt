package com.example.retromariokmm.domain.usecases.logout

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.utils.Resource
import kotlinx.coroutines.flow.Flow

class LogoutUseCase(private val repository : FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo

    suspend fun invoke(): Flow<Resource<Unit>> {
        return repository.logout()
    }
}