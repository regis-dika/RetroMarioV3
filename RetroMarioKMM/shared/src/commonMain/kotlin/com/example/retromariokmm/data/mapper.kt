package com.example.retromariokmm.data

import com.example.rtromariocomposeapp.domain.models.RetroUser
import com.example.rtromariocomposeapp.domain.models.UserComment
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

    val uid: String = this.get<String>("uid").toString()
    val name: String = this.get<String>("name").toString()
    val firstName: String = this.get<String>("firstName").toString()
    val url: String = this.get<String>("url").toString()
    val difficulty: Long = this.get<Long>("difficulty")
    val life: Long = this.get<Long>("life")
    val commentList: List<UserComment> = get<List<UserComment>>("starPostsList")

    return RetroUser(
        uid, name, url, life.toInt(), difficulty.toInt(), commentList.toMutableList(),
        mutableListOf()
    )
}
