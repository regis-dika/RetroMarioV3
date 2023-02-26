package com.example.retromariokmm.domain.models

@kotlinx.serialization.Serializable
sealed class IdentifiedObject {
    abstract val id: String
    abstract val creatorId: String
}
