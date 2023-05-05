package com.github.josh910830.portablemq

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("portable-mq")
data class PortableMQProperties(
    val consumer: Consumer = Consumer()
) {
    data class Consumer(
        val groupId: String = ""
    )
}
