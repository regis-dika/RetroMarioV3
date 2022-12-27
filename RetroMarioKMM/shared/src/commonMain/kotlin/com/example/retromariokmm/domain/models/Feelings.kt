package com.example.retromariokmm.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Feelings(
    val uid : String,
    val state : Long?
)
