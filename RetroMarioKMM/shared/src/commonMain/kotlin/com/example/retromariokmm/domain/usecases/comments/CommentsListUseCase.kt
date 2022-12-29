package com.example.retromariokmm.domain.usecases.comments

import com.example.retromariokmm.data.remote.FirebaseRetroMarioRepositoryImpl
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.domain.models.UserComment
import com.example.retromariokmm.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

//TODO Add Interface
class CommentsListUseCase(private val repository : FirebaseRetroMarioRepositoryImpl) {
    //TODO Inject repo
     fun invoke(path:String) = flow {
        emitAll(repository.getAllComments(path))
    }
}