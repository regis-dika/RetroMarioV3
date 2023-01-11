package com.example.retromariokmm.domain.models

data class RetroUser(
    val uid: String = "",
    val firstName: String? = null,
    val name: String? = null,
    val bitmap: String = "",
    var life: Int = -1,
    var difficulty: Int = -1,
)
