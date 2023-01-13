package com.example.retromariokmm.domain.repository

import com.example.retromariokmm.utils.Resource
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.domain.models.UserAction
import com.example.retromariokmm.domain.models.UserComment
import kotlinx.coroutines.flow.Flow

interface RetroMarioRepository {
    suspend fun createUser(email: String, password: String): Resource<Unit>
    suspend fun signIn(email: String, password: String): Resource<Unit>
    fun getRetroUsers(): Flow<Resource<List<RetroUser>>>
    suspend fun updateLife(life: Int): Flow<Resource<Unit>>
    suspend fun updateDifficulty(life: Int): Flow<Resource<Unit>>

    //Comments
    suspend fun getAllComments(path : String): Flow<Resource<List<UserComment>>>
    suspend fun createStarComment(path: String,description: String): Flow<Resource<Unit>>
    suspend fun updateComment(path :String,commentId : String, description :String): Flow<Resource<Unit>>
    suspend fun updateLikeComment(path: String,commentId : String,isLiked :Boolean?)
    suspend fun getCommentById(path: String,commentId : String): Flow<Resource<UserComment>>

    //Action
    suspend fun getAllActions(): Flow<Resource<List<UserAction>>>
    suspend fun createAction(title: String,description: String): Flow<Resource<Unit>>
    suspend fun getActionById(actionId : String): Flow<Resource<UserAction>>
    suspend fun updateAction(actionId :String,title: String,description: String): Flow<Resource<Unit>>
    suspend fun updateActorList(actionId :String,takeAction: Boolean)
    suspend fun updateActionCheckState(actionId :String,isCheck: Boolean)
}