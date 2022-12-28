package com.example.retromariokmm.domain.models

import com.example.retromariokmm.domain.models.UserAction

data class RetroUser(
    val uid :String = "",
    val name: String = "error name",
    val bitmap: String = "",
    var life: Int = -1,
    var difficulty: Int = -1,
)
