package com.github.josh910830.portablemq.support.autoconfiguration.memory

import com.github.josh910830.portablemq.kafka.consumer.badletter.Badletter
import com.github.josh910830.portablemq.kafka.consumer.badletter.BadletterStore
import com.github.josh910830.portablemq.support.autoconfiguration.DefaultBeanClass

class MemoryBadletterStore : DefaultBeanClass(), BadletterStore {

    private val map = mutableMapOf<String, Badletter>()

    override fun save(badletter: Badletter) {
        map[badletter.id] = badletter
    }

}
