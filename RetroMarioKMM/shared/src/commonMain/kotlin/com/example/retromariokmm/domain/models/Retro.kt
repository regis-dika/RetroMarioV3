package com.example.retromariokmm.domain.models

@kotlinx.serialization.Serializable
data class Retro(
    override val id: String = "",
    override val creatorId: String = "",
    val title: String = "error creation retro",
    val description: String = "error creation retro",
    val users: List<String> = emptyList()
) : IdentifiedObject()
