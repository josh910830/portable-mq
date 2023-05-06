package com.github.josh910830.portablemq.core.consumer.deadletter

import com.github.josh910830.portablemq.core.consumer.Broker
import com.github.josh910830.portablemq.core.message.Message
import org.springframework.stereotype.Component
import java.util.*

@Component
class DeadletterHandler(
    private val deadletterStore: DeadletterStore,
    private val redriveTokenManager: RedriveTokenManager,
    private val deadletterNotifier: DeadletterNotifier,
    private val redriveProducerResolver: RedriveProducerResolver
) {

    fun create(topic: String, message: Message, broker: Broker, exception: Exception) {
        val deadletterId = UUID.randomUUID().toString()
        val deadletter = Deadletter(deadletterId, topic, message, broker, false)

        deadletterStore.save(deadletter)
        val redriveToken = redriveTokenManager.issue(deadletter.id)
        deadletterNotifier.notify(deadletter, redriveToken, exception)
    }


    fun redrive(deadletterId: String) {
        val deadletter = deadletterStore.find(deadletterId)

        with(deadletter) { redriveProducerResolver.get(broker).produce(topic, message) }

        deadletter.redriven = true
        deadletterStore.save(deadletter)
    }

}
