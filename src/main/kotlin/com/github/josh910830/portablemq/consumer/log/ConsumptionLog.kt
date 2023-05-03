package com.github.josh910830.portablemq.consumer.log

data class ConsumptionLog(
    val consumerGroup: String,
    val messageId: String
)
