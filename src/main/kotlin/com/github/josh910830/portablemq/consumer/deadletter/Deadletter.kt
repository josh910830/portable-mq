package com.github.josh910830.portablemq.consumer.deadletter

import com.github.josh910830.portablemq.message.Message

data class Deadletter(
    val id: String,
    val message: Message,
    val broker: Broker,
    var redriven: Boolean
)
