package com.example.retromariokmm.domain.usecases.retros

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.utils.Resource
import kotlinx.coroutines.flow.Flow

class CreateRetroUseCase(private val repository : FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo

    suspend fun invoke(title:String,description :String): Flow<Resource<String>> {
        return repository.createRetro(title, description)
    }
}