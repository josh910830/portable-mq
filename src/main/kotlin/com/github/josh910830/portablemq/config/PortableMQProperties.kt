package com.github.josh910830.portablemq.config

import com.github.josh910830.portablemq.consumer.deadletter.Broker
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("portable-mq")
data class PortableMQProperties(
    val default: Default
) {
    data class Default(
        val consumerGroup: String,
        val broker: Broker
    )
}
