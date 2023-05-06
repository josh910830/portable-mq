package com.github.josh910830.portablemq.kafka.consumer.deadletter

import com.github.josh910830.portablemq.core.consumer.Broker.KAFKA
import com.github.josh910830.portablemq.core.consumer.deadletter.RedriveProducer
import com.github.josh910830.portablemq.core.message.Message
import com.github.josh910830.portablemq.producer.kafka.KafkaTemplateHolder
import com.github.josh910830.portablemq.producer.kafka.ObjectMapperHolder
import org.springframework.stereotype.Component

@Component
class KafkaRedriveProducer : RedriveProducer {

    override val broker = KAFKA

    override fun produce(topic: String, message: Message) {
        val data = ObjectMapperHolder.get().writeValueAsString(message)
        KafkaTemplateHolder.get().send(topic, data)
    }

}
