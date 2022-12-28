package com.example.retromariokmm.domain.usecases.actions

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.domain.models.UserAction
import com.example.retromariokmm.domain.models.UserComment
import com.example.retromariokmm.utils.Resource
import kotlinx.coroutines.flow.Flow
//TODO Add Interface
class ActionListUseCase(private val repository : FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo
    suspend fun invoke(): Flow<Resource<List<UserAction>>> {
        return repository.getAllActions()
    }
}