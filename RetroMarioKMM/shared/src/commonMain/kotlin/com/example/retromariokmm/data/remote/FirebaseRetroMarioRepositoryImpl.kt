package com.example.retromariokmm.data.remote

import com.example.retromariokmm.data.toRetro
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
import dev.gitlive.firebase.firestore.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class FirebaseRetroMarioRepositoryImpl() : RetroMarioRepository {

    private val fireStore: FirebaseFirestore = Firebase.firestore
    private val firebaseAuth: FirebaseAuth = Firebase.auth

    private val retroCollection = fireStore.collection("retros")
    private val userCollection = fireStore.collection("users")
    private val actionCollection = fireStore.collection("actions")

    var currentUser: RetroUser? = null
        get() = field
        private set(value) {
            field = value
        }

    var currentRetroSession: Retro? = null
        get() = field
        private set(value) {
            field = value
        }

    override suspend fun createRetro(title: String, description: String) = flow {
        emit(Loading())
        try {
            if (currentUser != null) {
                val newDocument = retroCollection.document
                val retro = Retro(newDocument.id, title, description)
                retroCollection.document(newDocument.id).set(retro)
                emit(Success(newDocument.id))
            } else {
                emit(Error<String>("current user null"))
            }
        } catch (e: Exception) {
            emit(Error<String>(e.toString()))
        }
    }

    override suspend fun connectToRetro(retroId: String) {
        currentRetroSession = retroCollection.document(retroId).get().toRetro()
    }

    override suspend fun getMyRetros() = getUpdatedCollection<Retro>(retroCollection) { querySnapshot ->
        querySnapshot.documents.map { dc ->
            dc.toRetro()
        }.filter { retro -> retro.users.contains(currentUser?.uid) }.map { it }
    }

    override suspend fun updateRetro(): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun addCurrentUserToRetroWithLink(retroId: String) = flow {
        emit(Loading())
        try {
            currentUser?.let {
                retroCollection.document(retroId).update(Pair("users", FieldValue.arrayUnion(it.uid)))
            }
            emit(Success(Unit))
        } catch (e: Exception) {
            emit(Error(e.toString()))
        }
    }

    override suspend fun createUser(email: String, password: String): Resource<Unit> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password)
            val user = result.user
            if (user != null) {
                currentUser =
                    getRetroUsers().firstOrNull { it is Success }?.value?.first { it.uid == user.uid }
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
                currentUser =
                    getRetroUsers().firstOrNull { it is Success }?.value?.first { it.uid == user.uid }
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

    override fun getRetroUsers(): Flow<Resource<List<RetroUser>>> = getUpdatedCollection<RetroUser>(userCollection) {
        val userIdList = currentRetroSession?.users
        if (userIdList != null) {
            it.documents.filter { ds ->
                userIdList.contains(ds.id)
            }.map { dc ->
                dc.toRetroUser()
            }
        } else {
            it.documents.map { dc ->
                dc.toRetroUser()
            }
        }
    }

    override suspend fun updateLife(life: Int) = updateDocument(docRef = null, hashMapOf(Pair("life", life)))

    override suspend fun updateDifficulty(difficulty: Int) =
        updateDocument(docRef = null, hashMapOf(Pair("difficulty", difficulty)))

    override suspend fun getAllComments(path: String) = getUpdatedCollection<UserComment>(
        retroCollection.document(currentRetroSession?.id ?: "")
            .collection(path)
    ) { qs ->
        qs.documents.map { dc ->
            dc.toUserComment()
        }
    }

    override suspend fun createStarComment(path: String, description: String) =
        createCollectionFlow(
            path,
            UserComment(description = description)
        )

    override suspend fun updateComment(path: String, commentId: String, description: String) = updateDocument(
        Pair(path, commentId),
        hashMapOf(Pair("description", description))
    )

    override suspend fun updateLikeComment(path: String, commentId: String, isLiked: Boolean?) {
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

    override suspend fun getCommentById(path: String, commentId: String) = flow {
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

    override suspend fun getAllActions(): Flow<Resource<List<UserAction>>> = getUpdatedCollection(
        retroCollection.document(currentRetroSession?.id ?: "")
            .collection("actions")
    ) { querysnapshot ->
        querysnapshot.documents.map { dc ->
            dc.toUserAction()
        }
    }

    override suspend fun createAction(title: String, description: String) =
        createCollectionFlow(
            path = "actions",
            newObject = UserAction(title = title, description = description)
        )

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

    override suspend fun updateAction(actionId: String, title: String, description: String) =
        updateDocument(
            Pair(actionCollection.path, actionId), hashMapOf(
                Pair("title", title),
                Pair("description", description)
            )
        )

    override suspend fun updateActorList(actionId: String, takeAction: Boolean) {
        try {
            currentUser?.let { retroUser ->
                val docRef = actionCollection.document(actionId)
                if (takeAction) {
                    val actionActor = retroUser.firstName?.let { ActionActor(it, retroUser.bitmap) }
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

    //generic function

    fun createCollectionFlow(path: String, newObject: IdentifiedObject) =
        flow {
            val retroId = currentRetroSession?.id
            if (retroId != null) {
                if (currentUser != null) {
                    emit(Loading())
                    try {
                        val newDocument = retroCollection.document(retroId).collection(path).document

                        val obj = when (newObject) {
                            is Retro -> newObject.copy(id = newDocument.id, creatorId = currentUser!!.uid)
                            is UserComment -> newObject.copy(id = newDocument.id, creatorId = currentUser!!.uid)
                            is UserAction -> newObject.copy(id = newDocument.id, creatorId = currentUser!!.uid)
                        }
                        newDocument.set(obj)
                        emit(Success(newDocument.id))
                    } catch (e: Exception) {
                        emit(Error<String>(e.toString()))
                    }
                } else {
                    emit(Error<String>("current user null"))
                }
            } else {
                emit(Error("Error no current retro"))
            }
        }

    fun <T> getUpdatedCollection(collectionRef: CollectionReference, transform: ((QuerySnapshot) -> List<T>)) =
        callbackFlow {
            collectionRef.snapshots.collect {
                val updatedList = transform.invoke(it)
                trySend(Success(updatedList))
            }
            awaitClose()
        }

    fun updateDocument(docRef: Pair<String, String>? = null, hasMap: HashMap<String, Any>) = flow {
        emit(Loading())
        val mutableHashMap: MutableMap<String, Any> = hasMap

        val retroId = currentRetroSession?.id
        if (retroId != null) {
            val retroDocRef = retroCollection.document(retroId)
            try {
                if (currentUser != null) {
                    mutableHashMap["authorId"] = currentUser!!.uid
                    mutableHashMap.toMap()

                    // add cocument ref in addition
                    if (docRef == null) {
                        retroDocRef.update(hasMap)
                    } else {
                        retroDocRef.collection(docRef.first)
                            .document(docRef.second)
                            .update(hasMap)
                    }
                    emit(Success(Unit))
                } else {
                    emit(Error("Error no current user"))
                }
            } catch (e: Exception) {
                emit(Error(e.toString()))
            }
        } else {
            emit(Error("Error no current retro"))
        }
    }
}