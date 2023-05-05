package com.github.josh910830.portablemq.producer.spring

import com.github.josh910830.portablemq.message.Message
import com.github.josh910830.portablemq.producer.BrokerProducer
import com.github.josh910830.portablemq.producer.TopicExtractionUtils.Companion.extractTopic

interface SpringProducer<T : Message> : BrokerProducer<T> {

    override fun produce(message: T) {
        val topic = extractTopic(javaClass, message)

        // TODO produce with topic to be dispatched by single @EventListener
        ApplicationEventPublisherHolder.get().publishEvent(message)
    }

}
