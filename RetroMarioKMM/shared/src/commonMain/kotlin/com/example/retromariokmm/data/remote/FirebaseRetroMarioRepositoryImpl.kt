package com.example.retromariokmm.data.remote

import com.example.retromariokmm.data.toRetroUser
import com.example.retromariokmm.data.toUserComment
import com.example.retromariokmm.domain.repository.RetroMarioRepository
import com.example.retromariokmm.utils.Resource
import com.example.retromariokmm.utils.Success
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.domain.models.UserAction
import com.example.retromariokmm.domain.models.UserComment
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class FirebaseRetroMarioRepositoryImpl() : RetroMarioRepository {

    private val fireStore: FirebaseFirestore = Firebase.firestore
    private val firebaseAuth: FirebaseAuth = Firebase.auth

    private val userCollection = fireStore.collection("users")
    private val startCommentsCollection = fireStore.collection("starPostsList")

    var currentUser: RetroUser? = null
    get() = field
    private set(value){
        field = value
    }


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
                currentUser = user.toRetroUser()
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

    override suspend fun setLifeDifficulty(life: Int, difficulty: Int) = flow {
        emit(Loading())
        try {
            currentUser?.let {
                userCollection.document(it.uid).update(hashMapOf(Pair("life", life)))
                userCollection.document(it.uid).update(hashMapOf(Pair("difficulty", difficulty)))
            }
            emit(Success(Unit))
        } catch (e: Exception) {
            emit(Error(e.toString()))
        }
    }

    override suspend fun getAllComments(): Flow<Resource<List<UserComment>>> = callbackFlow {
        startCommentsCollection.snapshots.collect {
            val updatedList = it.documents.map { dc ->
                dc.toUserComment()
            }
            trySend(Success(updatedList))
        }
        awaitClose()
    }

    override suspend fun createStarComment(description: String) = flow {
        emit(Loading())
        try {
            currentUser?.let {
                val docRef = startCommentsCollection.document
                val createdComment = UserComment(postId = docRef.id, authorId = it.uid, description = description )
                docRef.set(createdComment)
                emit(Success(Unit))
            }
        }catch (e: Exception){
            emit(Error<Unit>(e.toString()))
        }
    }

    override suspend fun updateComment(commentId : String, description :String) = flow {
        emit(Loading())
        try {
            currentUser?.let {
                val docRef = startCommentsCollection.document(commentId)
                docRef.update(hashMapOf(Pair("description", description),(Pair("authorId", it.uid))))
                emit(Success(Unit))
            }
        }catch (e: Exception){
            emit(Error<Unit>(e.toString()))
        }
    }

    override suspend fun getCommentById(commentId: String) = flow {
        emit(Loading())
        try {
            currentUser?.let {
                val doc = startCommentsCollection.document(commentId).get()
                emit(Success(doc.toUserComment()))
            }
        }catch (e: Exception){
            emit(Error<UserComment>(e.toString()))
        }
    }

    override suspend fun getAllActions(): Flow<Resource<List<UserAction>>> {
        TODO("Not yet implemented")
    }

    override suspend fun setAction(userAction: UserAction): Flow<Resource<List<UserAction>>> {
        TODO("Not yet implemented")
    }

}