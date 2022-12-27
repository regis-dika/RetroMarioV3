package com.example.retromariokmm.domain.usecases.comments

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.domain.models.UserComment
import com.example.retromariokmm.utils.Resource
import kotlinx.coroutines.flow.Flow
//TODO Add Interface
class GetCommentByIdUseCase(private val repository : FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo
    suspend fun invoke(commentId :String): Flow<Resource<UserComment>> {
        return repository.getCommentById(commentId)
    }
}