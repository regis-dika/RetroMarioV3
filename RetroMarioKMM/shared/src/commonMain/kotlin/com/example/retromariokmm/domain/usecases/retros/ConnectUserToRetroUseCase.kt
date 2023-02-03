package com.example.retromariokmm.domain.usecases.retros

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.utils.Resource
import kotlinx.coroutines.flow.Flow

class ConnectUserToRetroUseCase(private val repository: FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo

    suspend fun invoke(retroId: String) {
        return repository.connectToRetro(retroId)
    }
}