package com.example.retromariokmm.domain.models

import com.example.retromariokmm.domain.models.UserAction

data class RetroUser(
    val uid :String,
    val name: String,
    val bitmap: String,
    var life: Int,
    var difficulty: Int,
    val commentsList: MutableList<UserComment>,
    val actionList: MutableList<UserAction>

) {
    companion object {
        fun errorRetroUser(): RetroUser = RetroUser(
            "uid","no Name", "no bitmap", -1, -1, mutableListOf(),
            mutableListOf()
        )
    }
}
