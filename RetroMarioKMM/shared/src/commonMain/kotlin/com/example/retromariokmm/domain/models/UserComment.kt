package com.example.retromariokmm.domain.models

data class UserComment(
    val postId: String = "",
    val authorId: String = "",
    val description: String = "",
    val feelings: List<Feelings> = emptyList()
)
