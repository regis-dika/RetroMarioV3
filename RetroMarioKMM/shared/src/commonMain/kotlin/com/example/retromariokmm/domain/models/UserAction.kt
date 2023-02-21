package com.example.retromariokmm.domain.models

import kotlinx.serialization.Polymorphic

@Polymorphic
@kotlinx.serialization.Serializable
data class UserAction(
    override val id: String = "",
    override val creatorId: String = "",
    val title: String = "",
    val description: String = "",
    val isCheck: Boolean = false,
    val actorList: HashMap<String, ActionActor>? = null
) : IdentifiedObject