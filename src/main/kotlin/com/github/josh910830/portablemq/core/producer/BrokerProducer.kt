package com.github.josh910830.portablemq.core.producer

import com.github.josh910830.portablemq.core.message.Message

interface BrokerProducer<T : Message> {

    fun produce(message: T)

}
