package com.github.josh910830.portablemq.core.consumer.deadletter

interface DeadletterStore {

    fun save(deadletter: Deadletter)
    fun find(deadletterId: String): Deadletter
    fun findAllNotRedriven(): List<Deadletter>

}
