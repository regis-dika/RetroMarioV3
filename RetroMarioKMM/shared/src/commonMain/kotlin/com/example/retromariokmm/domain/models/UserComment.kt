package com.example.retromariokmm.domain.models

@kotlinx.serialization.Serializable
data class UserComment(
    override val id: String = "",
    override val creatorId: String = "",
    val description: String = "",
    val feelings: HashMap<String, Feelings>? = null
) : IdentifiedObject()
