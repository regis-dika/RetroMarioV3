package com.example.retromariokmm.domain.usecases.users

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UpdateDifficultyUseCase(private val repository : FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo

    suspend fun invoke(difficulty:Int): Flow<Resource<Unit>> {
        return repository.updateDifficulty(difficulty).map {
            it
        }
    }
}