package com.example.retromariokmm.domain.usecases.users

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SetLifeDifficultyUseCase(private val repository : FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo

    suspend fun invoke(life: Int, difficulty:Int): Flow<Resource<Unit>> {
        return repository.setLifeDifficulty(life,difficulty).map {
            it
        }
    }
}