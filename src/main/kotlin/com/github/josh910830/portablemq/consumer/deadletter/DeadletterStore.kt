package com.github.josh910830.portablemq.consumer.deadletter

interface DeadletterStore {

    fun save(deadletter: Deadletter)
    fun find(deadletterId: String): Deadletter

}
