package com.github.josh910830.portablemq.core.consumer.log

import com.github.josh910830.portablemq.core.message.Message

data class ConsumptionLog(
    val groupId: String,
    val message: Message
)
