package com.github.josh910830.portablemq.message.spring

import com.github.josh910830.portablemq.core.message.Message

data class SpringMessageEvent(
    val topic: String,
    val message: Message
)
