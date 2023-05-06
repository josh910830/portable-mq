package com.github.josh910830.portablemq.spring.producer

import com.github.josh910830.portablemq.core.message.Message
import com.github.josh910830.portablemq.spring.event.SpringMessageEvent
import com.github.josh910830.portablemq.core.producer.BrokerProducer
import com.github.josh910830.portablemq.core.producer.Producer

interface SpringProducer<T : Message> : BrokerProducer<T> {

    override fun produce(message: T) {
        val topic = javaClass.getAnnotation(Producer::class.java)!!.topic
        val event = SpringMessageEvent(topic, message)
        ApplicationEventPublisherHolder.get().publishEvent(event)
    }

}