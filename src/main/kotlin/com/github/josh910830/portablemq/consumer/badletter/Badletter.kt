package com.github.josh910830.portablemq.consumer.badletter

import com.github.josh910830.portablemq.consumer.deadletter.Broker

data class Badletter(
    val id: String,
    val topic: String,
    val data: String,
    val broker: Broker
)
