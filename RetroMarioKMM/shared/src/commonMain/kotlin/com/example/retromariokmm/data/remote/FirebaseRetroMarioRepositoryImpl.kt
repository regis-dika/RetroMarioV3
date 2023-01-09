package com.example.retromariokmm.data.remote

import com.example.retromariokmm.data.toRetroUser
import com.example.retromariokmm.data.toUserAction
import com.example.retromariokmm.data.toUserComment
import com.example.retromariokmm.domain.models.*
import com.example.retromariokmm.domain.repository.RetroMarioRepository
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Resource
import com.example.retromariokmm.utils.Success
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class FirebaseRetroMarioRepositoryImpl() : RetroMarioRepository {

    private val fireStore: FirebaseFirestore = Firebase.firestore
    private val firebaseAuth: FirebaseAuth = Firebase.auth

    private val userCollection = fireStore.collection("users")
    private val actionCollection = fireStore.collection("actions")

    var currentUser: RetroUser? = null
        get() = field
        private set(value) {
            field = value
        }

    override suspend fun createUser(email: String, password: String): Resource<Unit> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password)
            val user = result.user
            if (user != null) {
                currentUser = getRetroUsers().firstOrNull { it is Success }?.value?.first { it.uid == user.uid }
                if (currentUser != null) {
                    Success(Unit)
                } else {
                    Error("current user is null")
                }
            } else {
                Error("error user is null")
            }
        } catch (e: Exception) {
            Error(e.toString())
        }
    }

    override suspend fun signIn(email: String, password: String): Resource<Unit> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password)
            val user = result.user
            if (user != null) {
                currentUser = getRetroUsers().firstOrNull { it is Success }?.value?.first { it.uid == user.uid }
                if (currentUser != null) {
                    Success(Unit)
                } else {
                    Error("current user is null")
                }
            } else {
                Error("error user is null")
            }
        } catch (e: Exception) {
            Error(e.toString())
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

    override suspend fun getAllComments(path : String): Flow<Resource<List<UserComment>>> = callbackFlow {
        fireStore.collection(path).snapshots.collect {
            val updatedList = it.documents.map { dc ->
                dc.toUserComment()
            }
            trySend(Success(updatedList))
        }
        awaitClose()
    }

    override suspend fun createStarComment(path: String,description: String) = flow {
        emit(Loading())
        try {
            currentUser?.let {
                val docRef = fireStore.collection(path).document
                val createdComment = UserComment(postId = docRef.id, authorId = it.uid, description = description)
                docRef.set(createdComment)
                emit(Success(Unit))
            }
        } catch (e: Exception) {
            emit(Error<Unit>(e.toString()))
        }
    }

    override suspend fun updateComment(path: String,commentId: String, description: String) = flow {
        emit(Loading())
        try {
            currentUser?.let {
                val docRef = fireStore.collection(path).document(commentId)
                docRef.update(hashMapOf(Pair("description", description), (Pair("authorId", it.uid))))
                emit(Success(Unit))
            }
        } catch (e: Exception) {
            emit(Error<Unit>(e.toString()))
        }
    }

    override suspend fun updateLikeComment(path: String,commentId: String, isLiked: Boolean?) {
        try {
            currentUser?.let {
                val docRef = fireStore.collection(path).document(commentId)
                val updatedFeelings = Feelings(
                    it.uid,
                    when (isLiked) {
                        true -> 1
                        false -> -1
                        else -> 0
                    }
                )
                val hashMap = hashMapOf("feelings" to hashMapOf(it.uid to updatedFeelings))
                docRef.set(hashMap, merge = true)
            }
        } catch (e: Exception) {
        }
    }

    override suspend fun getCommentById(path: String,commentId: String) = flow {
        emit(Loading())
        try {
            currentUser?.let {
                val doc = fireStore.collection(path).document(commentId).get()
                emit(Success(doc.toUserComment()))
            }
        } catch (e: Exception) {
            emit(Error<UserComment>(e.toString()))
        }
    }

    override suspend fun getAllActions(): Flow<Resource<List<UserAction>>> = callbackFlow {
        actionCollection.snapshots.collect {
            val updatedList = it.documents.map { dc ->
                dc.toUserAction()
            }
            trySend(Success(updatedList))
        }
        awaitClose()
    }

    override suspend fun createAction(title: String, description: String) = flow {
        emit(Loading())
        try {
            currentUser?.let {
                val docRef = actionCollection.document
                val createdComment =
                    UserAction(actionId = docRef.id, authorId = it.uid, title = title, description = description)
                docRef.set(createdComment)
                emit(Success(Unit))
            }
        } catch (e: Exception) {
            emit(Error<Unit>(e.toString()))
        }
    }

    override suspend fun getActionById(actionId: String) = flow {
        emit(Loading())
        try {
            currentUser?.let {
                val doc = actionCollection.document(actionId).get()
                emit(Success(doc.toUserAction()))
            }
        } catch (e: Exception) {
            emit(Error<UserAction>(e.toString()))
        }
    }

    override suspend fun updateAction(actionId: String, title: String, description: String) = flow {
        emit(Loading())
        try {
            currentUser?.let {
                val docRef = actionCollection.document(actionId)
                docRef.update(
                    hashMapOf(
                        Pair("title", title),
                        Pair("description", description),
                        (Pair("authorId", it.uid))
                    )
                )
                emit(Success(Unit))
            }
        } catch (e: Exception) {
            emit(Error<Unit>(e.toString()))
        }
    }

    override suspend fun updateActorList(actionId: String, takeAction: Boolean) {
        try {
            currentUser?.let { retroUser ->
                val docRef = actionCollection.document(actionId)
                if (takeAction) {
                    val actionActor = ActionActor(retroUser.firstName,retroUser.bitmap)
                    val actorMapUpdated = hashMapOf("actorList" to hashMapOf(retroUser.uid to actionActor))
                    docRef.set(actorMapUpdated, merge = true)
                } else {
                    val currentDoc = docRef.get().toUserAction()
                    currentDoc.actorList?.remove(retroUser.uid)
                    val docActorListRemoved = currentDoc.copy(actorList = currentDoc.actorList)
                    docRef.set(docActorListRemoved)
                }
            }
        } catch (e: Exception) {
            readln()
        }
    }

    override suspend fun updateActionCheckState(actionId: String, isCheck: Boolean) {
        try {
            currentUser?.let {
                val docRef = actionCollection.document(actionId)
                val actorMap = hashMapOf("isCheck" to isCheck)
                docRef.set(actorMap, merge = true)
            }
        } catch (e: Exception) {
        }
    }
}