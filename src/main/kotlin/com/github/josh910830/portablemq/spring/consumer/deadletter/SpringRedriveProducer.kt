package com.github.josh910830.portablemq.spring.consumer.deadletter

import com.github.josh910830.portablemq.core.consumer.Broker.SPRING
import com.github.josh910830.portablemq.core.consumer.deadletter.RedriveProducer
import com.github.josh910830.portablemq.core.message.Message
import com.github.josh910830.portablemq.spring.event.SpringMessageEvent
import com.github.josh910830.portablemq.spring.producer.ApplicationEventPublisherHolder
import org.springframework.stereotype.Component

@Component
class SpringRedriveProducer : RedriveProducer {

    override val broker = SPRING

    override fun produce(topic: String, message: Message) {
        val event = SpringMessageEvent(topic, message)
        ApplicationEventPublisherHolder.get().publishEvent(event)
    }

}
