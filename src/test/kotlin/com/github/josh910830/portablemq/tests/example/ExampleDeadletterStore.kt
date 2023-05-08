package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.core.consumer.deadletter.Deadletter
import com.github.josh910830.portablemq.core.consumer.deadletter.DeadletterStore
import org.springframework.boot.test.context.TestComponent

@TestComponent
class ExampleDeadletterStore : DeadletterStore {

    private val map = mutableMapOf<String, Deadletter>()

    override fun save(deadletter: Deadletter) {
        map[deadletter.id] = deadletter
    }

    override fun findAll(): List<Deadletter> {
        return map.values.toList()
    }

    override fun findById(deadletterId: String): Deadletter {
        return map[deadletterId]!!
    }

    override fun deleteById(deadletterId: String) {
        map.remove(deadletterId)
    }

    override fun clear() {
        map.clear()
    }

}
