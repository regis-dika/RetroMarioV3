package com.example.retromariokmm.data.remote

import com.example.retromariokmm.data.toRetroUser
import com.example.retromariokmm.domain.repository.RetroMarioRepository
import com.example.retromariokmm.utils.Resource
import com.example.retromariokmm.utils.Success
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.domain.models.UserAction
import com.example.retromariokmm.domain.models.UserComment
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseRetroMarioRepositoryImpl() : RetroMarioRepository {

    private val fireStore: FirebaseFirestore = Firebase.firestore
    private val firebaseAuth: FirebaseAuth = Firebase.auth

    private val userCollection = fireStore.collection("users")

    override suspend fun createUser(email: String, password: String): Resource<RetroUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password)
            val user = result.user
            if (user != null) {
                Success(user.toRetroUser())
            } else {
                com.example.retromariokmm.utils.Error("error user is null")
            }
        } catch (e: Exception) {
            com.example.retromariokmm.utils.Error(e.toString())
        }
    }

    override suspend fun signIn(email: String, password: String): Resource<RetroUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password)
            val user = result.user
            if (user != null) {
                Success(user.toRetroUser())
            } else {
                com.example.retromariokmm.utils.Error("error user is null")
            }
        } catch (e: Exception) {
            com.example.retromariokmm.utils.Error(e.toString())
        }
    }

    override fun getRetroUsers(): Flow<Resource<List<RetroUser>>> = callbackFlow {
        userCollection.snapshots.collect {
            val updatedList = it.documents.map { dc ->
                dc.toRetroUser()
            }
            trySend(Success(updatedList))
        }
        awaitClose()
    }

    override suspend fun getAllComments(): Flow<Resource<List<UserComment>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllActions(): Flow<Resource<List<UserAction>>> {
        TODO("Not yet implemented")
    }

}