package com.github.josh910830.portablemq.producer.spring

import com.github.josh910830.portablemq.message.Message
import com.github.josh910830.portablemq.message.spring.SpringMessageEvent
import com.github.josh910830.portablemq.producer.BrokerProducer
import com.github.josh910830.portablemq.producer.TopicExtractionUtils.Companion.extractTopic

interface SpringProducer<T : Message> : BrokerProducer<T> {

    override fun produce(message: T) {
        val topic = extractTopic(javaClass, message)
        val event = SpringMessageEvent(topic, message)
        ApplicationEventPublisherHolder.get().publishEvent(event)
    }

}
