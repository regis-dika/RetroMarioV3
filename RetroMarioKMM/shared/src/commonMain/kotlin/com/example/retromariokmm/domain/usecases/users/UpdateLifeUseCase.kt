package com.example.retromariokmm.domain.usecases.users

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UpdateLifeUseCase(private val repository : FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo

    suspend fun invoke(life: Int): Flow<Resource<Unit>> {
        return repository.updateLife(life).map {
            it
        }
    }
}