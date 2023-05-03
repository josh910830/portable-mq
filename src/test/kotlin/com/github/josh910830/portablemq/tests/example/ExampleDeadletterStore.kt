package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.consumer.deadletter.Deadletter
import com.github.josh910830.portablemq.consumer.deadletter.DeadletterStore

class ExampleDeadletterStore : DeadletterStore {

    private val map = mutableMapOf<String, Deadletter>()

    override fun save(deadletter: Deadletter) {
        map[deadletter.id] = deadletter
    }

    override fun find(deadletterId: String): Deadletter {
        return map[deadletterId]!!
    }

}
