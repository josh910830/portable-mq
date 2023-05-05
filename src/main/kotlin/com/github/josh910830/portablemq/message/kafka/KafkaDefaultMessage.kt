package com.github.josh910830.portablemq.message.kafka

import com.github.josh910830.portablemq.message.Message

data class KafkaDefaultMessage(
    override val id: String
) : Message
