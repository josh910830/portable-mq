package com.github.josh910830.portablemq.producer.kafka

import com.github.josh910830.portablemq.message.Message
import com.github.josh910830.portablemq.producer.BrokerProducer
import com.github.josh910830.portablemq.utility.Extracts.Companion.extractTopic

interface KafkaProducer<T : Message> : BrokerProducer<T> {

    override fun produce(message: T) {
        val topic = extractTopic(javaClass, message)
        val data = ObjectMapperHolder.get().writeValueAsString(message)

        KafkaTemplateHolder.get().send(topic, data)
    }

}
