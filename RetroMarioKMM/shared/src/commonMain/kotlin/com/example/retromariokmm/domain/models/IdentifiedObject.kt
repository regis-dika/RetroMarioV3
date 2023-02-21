package com.example.retromariokmm.domain.models

@kotlinx.serialization.Serializable
sealed interface IdentifiedObject {
    val id: String
    val creatorId: String
}
