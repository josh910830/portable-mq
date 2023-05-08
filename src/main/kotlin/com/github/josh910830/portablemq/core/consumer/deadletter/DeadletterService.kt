package com.github.josh910830.portablemq.core.consumer.deadletter

import com.github.josh910830.portablemq.core.consumer.deadletter.interfaces.DeadletterStore
import com.github.josh910830.portablemq.core.consumer.deadletter.interfaces.RedriveTokenManager
import com.github.josh910830.portablemq.core.exception.PortableMQException
import org.springframework.stereotype.Service

@Service
class DeadletterService(
    private val deadletterStore: DeadletterStore,
    private val redriveProducerResolver: RedriveProducerResolver,
    private val redriveTokenManager: RedriveTokenManager
) {

    fun find(topic: String? = null, redriven: Boolean? = null): List<Deadletter> {
        return deadletterStore.findAll()
            .filter { topic?.let { t -> it.topic == t } ?: true }
            .filter { redriven?.let { r -> it.redriven == r } ?: true }
    }

    fun findById(deadletterId: String): Deadletter {
        return deadletterStore.findById(deadletterId)
    }


    fun redrive(deadletterId: String) {
        val deadletter = deadletterStore.findById(deadletterId)
        if (deadletter.redriven) throw PortableMQException("Already redriven deadletter.")

        with(deadletter) { redriveProducerResolver.get(broker).produce(topic, message) }

        deadletter.redriven = true
        deadletterStore.save(deadletter)
    }

    fun authenticate(deadletterId: String, redriveToken: String): Boolean {
        return redriveTokenManager.authenticate(deadletterId, redriveToken)
    }


    fun drop(deadletterId: String) {
        deadletterStore.deleteById(deadletterId)
    }

    fun clear() {
        deadletterStore.clear()
    }

}
