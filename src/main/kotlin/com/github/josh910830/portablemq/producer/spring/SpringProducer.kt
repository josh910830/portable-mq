package com.github.josh910830.portablemq.producer.spring

import com.github.josh910830.portablemq.message.Message
import com.github.josh910830.portablemq.producer.Producer

interface SpringProducer<T : Message> : Producer<T> {

    override fun produce(message: T) {
        ApplicationEventPublisherHolder.get().publishEvent(message)
    }

}
