package com.example.retromariokmm.domain.repository

import com.example.retromariokmm.utils.Resource
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.domain.models.UserAction
import com.example.retromariokmm.domain.models.UserComment
import kotlinx.coroutines.flow.Flow

interface RetroMarioRepository {
    suspend fun createUser(email: String, password :String) : Resource<RetroUser>
    suspend fun signIn(email: String,password: String): Resource<RetroUser>
    fun getRetroUsers() : Flow<Resource<List<RetroUser>>>
    suspend fun getAllComments() : Flow<Resource<List<UserComment>>>
    suspend fun getAllActions() : Flow<Resource<List<UserAction>>>
}