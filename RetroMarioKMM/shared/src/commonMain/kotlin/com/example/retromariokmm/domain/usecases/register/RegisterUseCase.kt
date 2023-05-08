package com.example.retromariokmm.domain.usecases.register

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.utils.Resource
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(private val repository: FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo

    suspend fun invoke(email: String, password: String, firstName: String, name: String,pictureUrl:String?): Flow<Resource<Unit>> {
        return repository.createUser(email, password, firstName, name,pictureUrl)
    }
}