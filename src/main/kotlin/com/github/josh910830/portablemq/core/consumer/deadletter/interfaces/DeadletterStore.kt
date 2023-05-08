package com.github.josh910830.portablemq.core.consumer.deadletter.interfaces

import com.github.josh910830.portablemq.core.consumer.deadletter.Deadletter

interface DeadletterStore {

    fun save(deadletter: Deadletter)
    fun findAll(): List<Deadletter>
    fun findById(deadletterId: String): Deadletter
    fun deleteById(deadletterId: String)
    fun clear()

}
