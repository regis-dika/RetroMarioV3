package com.example.retromariokmm.domain.models

data class UserAction(
    val author : String,
    val title : String,
    val description : String,
    val isCheck : Boolean,
    val actorList : List<String>
)