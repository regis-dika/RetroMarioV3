package com.example.retromariokmm.data

import com.example.retromariokmm.domain.models.Feelings
import com.example.retromariokmm.domain.models.RetroUser
import com.example.retromariokmm.domain.models.UserComment
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.DocumentSnapshot

fun FirebaseUser.toRetroUser() = RetroUser(
    this.uid,
    this.displayName ?: "noName",
    this.photoURL ?: "gs://retro-mario-ionic.appspot.com/Default/download.jpg",
    0,
    0,
    mutableListOf(),
    mutableListOf()
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
            uid, name, url, life.toInt(), difficulty.toInt(),
            mutableListOf()
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
