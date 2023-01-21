package com.example.retromariokmm.domain.usecases.retros

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.domain.models.Retro
import com.example.retromariokmm.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetAllRetrosUseCase(private val repository: FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo

    suspend fun invoke(): Flow<Resource<List<Retro>>> {
        return repository.getMyRetros()
    }
}