package com.github.josh910830.portablemq.producer

import com.github.josh910830.portablemq.message.Message

interface SpringProducer<T : Message> : Producer<T> {

    override fun produce(message: T) {
        ApplicationEventPublisherHolder.get().publishEvent(message)
    }

}
