package com.github.josh910830.portablemq.kafka.producer

import com.github.josh910830.portablemq.core.message.Message
import com.github.josh910830.portablemq.core.producer.PortableProducer
import com.github.josh910830.portablemq.core.producer.Producer

interface KafkaProducer<T : Message> : PortableProducer<T> {

    override fun produce(message: T) {
        val topic = javaClass.getAnnotation(Producer::class.java)!!.topic
        val data = ObjectMapperHolder.get().writeValueAsString(message)

        KafkaTemplateHolder.get().send(topic, data)
    }

}
