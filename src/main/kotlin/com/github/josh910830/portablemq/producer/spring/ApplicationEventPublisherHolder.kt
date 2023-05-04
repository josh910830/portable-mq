package com.github.josh910830.portablemq.producer.spring

import jakarta.annotation.PostConstruct
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class ApplicationEventPublisherHolder(
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    companion object {
        private lateinit var instance: ApplicationEventPublisher

        fun get(): ApplicationEventPublisher {
            return instance
        }
    }

    @PostConstruct
    fun initialize() {
        instance = applicationEventPublisher
    }

}
