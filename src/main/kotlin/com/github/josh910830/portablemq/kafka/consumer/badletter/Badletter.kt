package com.github.josh910830.portablemq.kafka.consumer.badletter

import com.github.josh910830.portablemq.core.consumer.Broker

data class Badletter(
    val id: String,
    val topic: String,
    val data: String,
    val broker: Broker
)
