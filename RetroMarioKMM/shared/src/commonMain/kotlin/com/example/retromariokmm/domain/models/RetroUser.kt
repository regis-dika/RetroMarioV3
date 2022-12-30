package com.example.retromariokmm.domain.models

data class RetroUser(
    val uid: String = "",
    val firstName: String = "error firstname",
    val name: String = "error name",
    val bitmap: String = "",
    var life: Int = -1,
    var difficulty: Int = -1,
)
