package com.example.retromariokmm.domain.models

@kotlinx.serialization.Serializable
data class Retro(
    val retroId : String,
    val title :String,
    val description : String,
    val users : List<RetroUser> = emptyList(),
    val starComments : List<UserComment> = emptyList(),
    val booComments : List<UserComment> = emptyList(),
    val goumbaComments : List<UserComment> = emptyList(),
    val mushroomComments : List<UserComment> = emptyList(),
    val actions : List<UserAction> = emptyList()
)
