package com.github.josh910830.portablemq.support.autoconfiguration.memory

import com.github.josh910830.portablemq.core.consumer.deadletter.Deadletter
import com.github.josh910830.portablemq.core.consumer.deadletter.DeadletterStore
import com.github.josh910830.portablemq.support.autoconfiguration.DefaultBeanClass

class MemoryDeadletterStore : DefaultBeanClass(), DeadletterStore {

    private val map = mutableMapOf<String, Deadletter>()


    override fun save(deadletter: Deadletter) {
        log.debug("save($deadletter)")
        map[deadletter.id] = deadletter
    }

    override fun findAll(): List<Deadletter> {
        log.debug("findAll()")
        return map.values.toList()
    }

    override fun findById(deadletterId: String): Deadletter {
        log.debug("findById($deadletterId)")
        return map[deadletterId]!!
    }

    override fun deleteById(deadletterId: String) {
        log.debug("deleteById($deadletterId)")
        map.remove(deadletterId)
    }

    override fun clear() {
        log.debug("clear()")
        map.clear()
    }

}
