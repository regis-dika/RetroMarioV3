package com.example.retromariokmm.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserAction(
    val authorId : String = "",
    val title : String = "",
    val description : String  = "",
    val isCheck : Boolean = false,
    val actorList : HashMap<String,String>? = null
)