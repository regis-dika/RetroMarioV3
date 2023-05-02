package com.example.retromariokmm.domain.usecases.comments

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.utils.Resource
import kotlinx.coroutines.flow.Flow
//TODO Add Interface
class UpdateCommentUseCase(private val repository : FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo
    suspend fun invoke(path:String,commentId :String,description :String): Flow<Resource<Unit>> {
        return repository.updateComment(path,commentId, description)
    }
}