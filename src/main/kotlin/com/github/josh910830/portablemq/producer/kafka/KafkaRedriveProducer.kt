package com.github.josh910830.portablemq.producer.kafka

import com.github.josh910830.portablemq.core.message.Message
import org.springframework.stereotype.Component

@Component
class KafkaRedriveProducer {

    fun produce(topic: String, message: Message) {
        val data = ObjectMapperHolder.get().writeValueAsString(message)
        KafkaTemplateHolder.get().send(topic, data)
    }

}
