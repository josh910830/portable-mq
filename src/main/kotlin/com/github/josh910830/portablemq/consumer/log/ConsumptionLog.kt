package com.github.josh910830.portablemq.consumer.log

import com.github.josh910830.portablemq.message.Message

data class ConsumptionLog(
    val consumerGroup: String,
    val message: Message
)
