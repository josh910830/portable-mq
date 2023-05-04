package com.github.josh910830.portablemq.producer.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class ObjectMapperHolder(
    private val objectMapper: ObjectMapper
) {

    companion object {
        private lateinit var instance: ObjectMapper

        fun get(): ObjectMapper {
            return instance
        }
    }

    @PostConstruct
    fun initialize() {
        instance = objectMapper
    }

}
