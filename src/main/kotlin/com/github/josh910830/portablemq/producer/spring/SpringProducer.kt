package com.github.josh910830.portablemq.producer.spring

import com.github.josh910830.portablemq.message.Message
import com.github.josh910830.portablemq.producer.BrokerProducer

interface SpringProducer<T : Message> : BrokerProducer<T> {

    override fun produce(message: T) {
        // TODO produce with topic to be dispatched by single @EventListener
        ApplicationEventPublisherHolder.get().publishEvent(message)
    }

}
