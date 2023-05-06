package com.github.josh910830.portablemq.tests.fixture

import com.github.josh910830.portablemq.core.consumer.Broker
import com.github.josh910830.portablemq.core.consumer.Broker.SPRING
import com.github.josh910830.portablemq.core.consumer.deadletter.Deadletter
import java.util.*

fun deadletterFixture(): Deadletter {
    return deadletterFixture(SPRING)
}

fun deadletterFixture(broker: Broker): Deadletter {
    val id = UUID.randomUUID().toString()
    val message = messageFixture()
    val topic = message.javaClass.name
    return Deadletter(id, topic, message, broker, false)
}
