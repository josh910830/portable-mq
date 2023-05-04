package com.github.josh910830.portablemq.producer.kafka

import jakarta.annotation.PostConstruct
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaTemplateHolder(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    companion object {
        private lateinit var instance: KafkaTemplate<String, String>

        fun get(): KafkaTemplate<String, String> {
            return instance
        }
    }

    @PostConstruct
    fun initialize() {
        instance = kafkaTemplate
    }

}
