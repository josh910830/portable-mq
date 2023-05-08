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

    override fun find(deadletterId: String): Deadletter {
        log.debug("find($deadletterId)")
        return map[deadletterId]!!
    }

    override fun findAllNotRedriven(): List<Deadletter> {
        log.debug("findAllNotRedriven()")
        return map.values.filterNot { it.redriven }
    }

}
