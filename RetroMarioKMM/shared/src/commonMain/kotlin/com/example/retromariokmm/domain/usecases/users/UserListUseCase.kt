package com.example.retromariokmm.domain.usecases.users

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.utils.Resource
import kotlinx.coroutines.flow.Flow
//TODO Add Interface
class UserListUseCase(private val repository : FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo
     fun invoke(): Flow<Resource<List<RetroUser>>> {
        return repository.getRetroUsers()
    }
}