package com.github.josh910830.portablemq.core.consumer.deadletter

import org.springframework.stereotype.Service

@Service
class DeadletterService(
    private val deadletterStore: DeadletterStore,
    private val redriveTokenManager: RedriveTokenManager,
    private val redriveProducerResolver: RedriveProducerResolver
) {

    fun redrive(deadletterId: String) {
        val deadletter = deadletterStore.find(deadletterId)

        with(deadletter) { redriveProducerResolver.get(broker).produce(topic, message) }

        deadletter.redriven = true
        deadletterStore.save(deadletter)
    }

}
