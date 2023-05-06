package com.github.josh910830.portablemq.core.consumer.deadletter

import com.github.josh910830.portablemq.core.consumer.Broker
import com.github.josh910830.portablemq.core.consumer.Broker.KAFKA
import com.github.josh910830.portablemq.core.consumer.Broker.SPRING
import com.github.josh910830.portablemq.core.message.Message
import com.github.josh910830.portablemq.producer.kafka.KafkaRedriveProducer
import com.github.josh910830.portablemq.producer.spring.SpringRedriveProducer
import org.springframework.stereotype.Component
import java.util.*

@Component
class DeadletterHandler(
    private val deadletterStore: DeadletterStore,
    private val redriveTokenManager: RedriveTokenManager,
    private val deadletterNotifier: DeadletterNotifier,
    private val springRedriveProducer: SpringRedriveProducer,
    private val kafkaRedriveProducer: KafkaRedriveProducer
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
        when (deadletter.broker) {
            SPRING -> springRedriveProducer.produce(deadletter.topic, deadletter.message)
            KAFKA -> kafkaRedriveProducer.produce(deadletter.topic, deadletter.message)
        }
        deadletter.redriven = true
        deadletterStore.save(deadletter)
    }

}
