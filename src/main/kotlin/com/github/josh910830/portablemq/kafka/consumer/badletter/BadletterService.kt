package com.github.josh910830.portablemq.kafka.consumer.badletter

import com.github.josh910830.portablemq.kafka.consumer.badletter.interfaces.BadletterStore
import org.springframework.stereotype.Service

@Service
class BadletterService(
    private val badletterStore: BadletterStore
) {

    fun find(topic: String? = null): List<Badletter> {
        return badletterStore.findAll()
            .filter { topic?.let { t -> it.topic == t } ?: true }
    }

    fun findById(deadletterId: String): Badletter {
        return badletterStore.findById(deadletterId)
    }


    fun drop(badletterId: String) {
        badletterStore.deleteById(badletterId)
    }

    fun clear() {
        badletterStore.clear()
    }

}
