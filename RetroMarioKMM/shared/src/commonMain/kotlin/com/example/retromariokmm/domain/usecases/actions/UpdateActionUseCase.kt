package com.example.retromariokmm.domain.usecases.actions

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.domain.models.UserComment
import com.example.retromariokmm.utils.Resource
import kotlinx.coroutines.flow.Flow
//TODO Add Interface
class UpdateActionUseCase(private val repository : FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo
    suspend fun invoke(actionId :String,title:String,description:String) : Flow<Resource<Unit>>{
        return repository.updateAction(actionId, title, description)
    }
}