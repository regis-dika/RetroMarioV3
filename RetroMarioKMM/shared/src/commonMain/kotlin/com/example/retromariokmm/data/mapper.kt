package com.example.retromariokmm.data

import com.example.retromariokmm.domain.models.Feelings
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.domain.models.UserAction
import com.example.retromariokmm.domain.models.UserComment
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.DocumentSnapshot

fun FirebaseUser.toRetroUser() = RetroUser(
    this.uid,
    this.displayName ?: "noName",
    this.photoURL ?: "gs://retro-mario-ionic.appspot.com/Default/download.jpg",
    0,
    0
)

fun DocumentSnapshot.toRetroUser(): RetroUser {

    return try {
        val uid: String = this.get<String>("uid").toString()
        val name: String = this.get<String>("name").toString()
        val firstName: String = this.get<String>("firstName").toString()
        val url: String = this.get<String>("url").toString()
        val difficulty: Long = this.get<Long>("difficulty")
        val life: Long = this.get<Long>("life")

        RetroUser(
            uid, name, url, life.toInt(), difficulty.toInt()
        )
    } catch (e: Exception) {
        RetroUser()
    }
}

fun DocumentSnapshot.toUserComment(): UserComment {

    return try {
        val postId = get<String>("postId").toString()
        val authorId = get<String>("authorId")
        val description = get<String>("description")
        val feelings = get<HashMap<String, Feelings>?>("feelings")
        UserComment(
            postId, authorId, description, feelings
        )

    } catch (e: Exception) {
        UserComment()
    }
}

fun DocumentSnapshot.toUserAction(): UserAction {

    return try {
        val actionId = get<String>("actionId")
        val authorId = get<String>("authorId")
        val title = get<String>("title")
        val description = get<String>("description")
        val isCheck = get<Boolean>("isCheck")
        val actorList = if(contains("actorList")){
            get<HashMap<String, String>?>("actorList")
        }else{
            null
        }
        UserAction(actionId,authorId, title, description, isCheck, actorList)
    } catch (e: Exception) {
        UserAction()
    }
}
