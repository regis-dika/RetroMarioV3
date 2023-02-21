package com.example.retromariokmm.domain.models

import kotlinx.serialization.Contextual

@kotlinx.serialization.Serializable
data class Retro(
    override val id : String = "",
    override val creatorId: String = "",
    val title :String = "error creation retro",
    val description : String = "error creation retro",
    val users : List<String> = emptyList(),
    val starComments : List<UserComment> = emptyList(),
    val booComments : List<UserComment> = emptyList(),
    val goumbaComments : List<UserComment> = emptyList(),
    val mushroomComments : List<UserComment> = emptyList(),
    val actions : List<UserAction> = emptyList()
): IdentifiedObject
