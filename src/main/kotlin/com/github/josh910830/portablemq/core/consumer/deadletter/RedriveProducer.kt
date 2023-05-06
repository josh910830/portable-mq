package com.github.josh910830.portablemq.core.consumer.deadletter

import com.github.josh910830.portablemq.core.consumer.Broker
import com.github.josh910830.portablemq.core.message.Message

interface RedriveProducer {

    val broker: Broker

    fun produce(topic: String, message: Message)

}
