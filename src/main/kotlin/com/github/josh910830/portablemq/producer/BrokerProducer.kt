package com.github.josh910830.portablemq.producer

import com.github.josh910830.portablemq.message.Message

interface BrokerProducer<T : Message> {

    fun produce(message: T)

}
