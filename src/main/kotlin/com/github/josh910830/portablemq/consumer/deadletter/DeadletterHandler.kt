package com.github.josh910830.portablemq.consumer.deadletter

import com.github.josh910830.portablemq.message.Message
import com.github.josh910830.portablemq.producer.SpringRedriveProducer
import org.springframework.stereotype.Component
import java.util.*

@Component
class DeadletterHandler(
    private val deadletterStore: DeadletterStore,
    private val redriveTokenManager: RedriveTokenManager,
    private val deadletterNotifier: DeadletterNotifier,
    private val springRedriveProducer: SpringRedriveProducer
) {

    fun create(message: Message, broker: Broker, exception: Exception) {
        val deadletterId = UUID.randomUUID().toString()
        val deadletter = Deadletter(deadletterId, message, broker, false)

        deadletterStore.save(deadletter)
        val redriveToken = redriveTokenManager.issue(deadletter.id)
        deadletterNotifier.notify(deadletter, redriveToken, exception)
    }


    fun redrive(deadletterId: String) {
        val deadletter = deadletterStore.find(deadletterId)
        when (deadletter.broker) {
            Broker.SPRING -> springRedriveProducer.produce(deadletter.message)
            Broker.KAFKA -> TODO("not yet implement")
        }
        deadletter.redriven = true
        deadletterStore.save(deadletter)
    }

}
