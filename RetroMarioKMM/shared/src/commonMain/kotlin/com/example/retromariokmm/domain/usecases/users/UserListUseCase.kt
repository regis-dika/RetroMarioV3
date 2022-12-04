package com.example.retromariokmm.domain.usecases.users

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.utils.Resource
import kotlinx.coroutines.flow.Flow
//TODO Add Interface
class UserListUseCase {
    //TODO Inject repo
    private val repository = FirebaseRetroMarioRepositoryImpl()

     fun invoke(): Flow<Resource<List<RetroUser>>> {
        return repository.getRetroUsers()
    }
}