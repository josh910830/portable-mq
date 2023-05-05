package com.github.josh910830.portablemq.producer.kafka

import com.github.josh910830.portablemq.message.Message
import com.github.josh910830.portablemq.producer.BrokerProducer

interface KafkaProducer<T : Message> : BrokerProducer<T> {

    override fun produce(message: T) {
        val topic = "topic" // TODO
        val data = ObjectMapperHolder.get().writeValueAsString(message)

        KafkaTemplateHolder.get().send(topic, data)
    }

}
