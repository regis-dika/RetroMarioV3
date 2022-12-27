package com.example.retromariokmm.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserComment(
    val postId: String = "",
    val authorId: String = "",
    val description: String = "",
    val feelings: HashMap<String, Feelings>? = null
)
