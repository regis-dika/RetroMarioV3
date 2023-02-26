package com.example.retromariokmm.domain.models

@kotlinx.serialization.Serializable
data class Feelings(
    val uid : String,
    val state : Long?
)
