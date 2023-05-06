package com.github.josh910830.portablemq.core.message

import java.util.*

abstract class IdentifiableMessage : Message {

    override val id: String = UUID.randomUUID().toString()

    final override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this::class != other::class) return false
        return id == (other as Message).id
    }

    final override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}
