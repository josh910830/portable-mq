package com.github.josh910830.portablemq.producer.kafka

import com.github.josh910830.portablemq.message.Message
import com.github.josh910830.portablemq.producer.Producer

interface KafkaProducer<T : Message> : Producer<T> {

    override fun produce(message: T) {
        val topic = "topic" // TODO
        val data = ObjectMapperHolder.get().writeValueAsString(message)

        KafkaTemplateHolder.get().send(topic, data)
    }

}
