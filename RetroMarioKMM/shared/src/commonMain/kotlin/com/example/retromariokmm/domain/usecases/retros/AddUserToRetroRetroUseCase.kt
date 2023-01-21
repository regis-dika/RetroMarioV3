package com.example.retromariokmm.domain.usecases.retros

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.utils.Resource
import kotlinx.coroutines.flow.Flow

class AddUserToRetroRetroUseCase(private val repository: FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo

    suspend fun invoke(retroId: String): Flow<Resource<Unit>> {
        return repository.addCurrentUserToRetroWithLink(retroId)
    }
}