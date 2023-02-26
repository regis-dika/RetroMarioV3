package com.example.retromariokmm.data

import com.example.retromariokmm.domain.models.*
import dev.gitlive.firebase.firestore.DocumentSnapshot

fun DocumentSnapshot.toRetro(): Retro {

    return try {
        val retroId = get<String>("id").toString()
        val creatorId = get<String>("creatorId").toString()
        val title = get<String>("title")
        val description = get<String>("description")
        val users = get<List<String>>("users")
        Retro(
            retroId,
            creatorId,
            title,
            description,
            users
        )
    } catch (e: Exception) {
        Retro()
    }
}

fun DocumentSnapshot.toRetroUser(): RetroUser {

    return try {
        val uid: String = this.get<String>("uid").toString()
        val firstName: String = this.get<String>("firstName").toString()
        val name: String = this.get<String>("name").toString()
        //TODO remove use only bitmap for future users
        val urlPicture = if (contains("bitmap")) {
            this.get<String>("bitmap").toString()
        } else {
            this.get<String>("url").toString()
        }
        val difficulty: Long = this.get<Long>("difficulty")
        val life: Long = this.get<Long>("life")

        RetroUser(
            uid, firstName, name, urlPicture, life.toInt(), difficulty.toInt()
        )
    } catch (e: Exception) {
        RetroUser()
    }
}

fun DocumentSnapshot.toUserComment(): UserComment {

    return try {
        val postId = get<String>("id").toString()
        val authorId = get<String>("creatorId")
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
        val actionId = get<String>("id")
        val authorId = get<String>("creatorId")
        val title = get<String>("title")
        val description = get<String>("description")
        val isCheck = get<Boolean>("isCheck")
        val actorList = if (contains("actorList")) {
            get<HashMap<String, ActionActor>?>("actorList")
        } else {
            null
        }
        UserAction(actionId, authorId, title, description, isCheck, actorList)
    } catch (e: Exception) {
        UserAction()
    }
}
