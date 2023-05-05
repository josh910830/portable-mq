package com.github.josh910830.portablemq.producer.spring

import com.github.josh910830.portablemq.message.Message
import com.github.josh910830.portablemq.message.spring.SpringMessageEvent
import org.springframework.stereotype.Component

@Component
class SpringRedriveProducer {

    fun produce(topic: String, message: Message) {
        val event = SpringMessageEvent(topic, message)
        ApplicationEventPublisherHolder.get().publishEvent(event)
    }

}
