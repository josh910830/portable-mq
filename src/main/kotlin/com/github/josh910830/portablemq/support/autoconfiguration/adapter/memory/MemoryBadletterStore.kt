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

}
