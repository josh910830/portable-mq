package com.github.josh910830.portablemq.spring.producer

import com.github.josh910830.portablemq.core.message.Message
import com.github.josh910830.portablemq.core.producer.PortableProducer
import com.github.josh910830.portablemq.core.producer.Producer
import com.github.josh910830.portablemq.spring.event.SpringMessageEvent

interface SpringProducer<T : Message> : PortableProducer<T> {

    override fun produce(message: T) {
        val topic = javaClass.getAnnotation(Producer::class.java)!!.topic
        val event = SpringMessageEvent(topic, message)
        ApplicationEventPublisherHolder.get().publishEvent(event)
    }

}
