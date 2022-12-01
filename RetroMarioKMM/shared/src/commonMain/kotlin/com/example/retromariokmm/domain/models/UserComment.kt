package com.example.retromariokmm.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserComment(
    val author : String,
    val nbLikes : Int,
    val nbDislikes : Int
)
