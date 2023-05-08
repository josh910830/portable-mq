package com.github.josh910830.portablemq.support.autoconfiguration.adapter.memory

import com.github.josh910830.portablemq.kafka.consumer.badletter.Badletter
import com.github.josh910830.portablemq.kafka.consumer.badletter.interfaces.BadletterStore
import com.github.josh910830.portablemq.support.autoconfiguration.adapter.DefaultBeanClass

class MemoryBadletterStore : DefaultBeanClass(), BadletterStore {

    private val map = mutableMapOf<String, Badletter>()

    override fun save(badletter: Badletter) {
        log.debug("save($badletter)")
        map[badletter.id] = badletter
    }

    override fun findAll(): List<Badletter> {
        log.debug("findAll()")
        return map.values.toList()
    }

    override fun findById(badletterId: String): Badletter {
        log.debug("findById($badletterId)")
        return map[badletterId]!!
    }

    override fun deleteById(badletterId: String) {
        log.debug("deleteById($badletterId)")
        map.remove(badletterId)
    }

    override fun clear() {
        log.debug("clear()")
        map.clear()
    }

}
