package com.github.josh910830.portablemq.tests.fixture

import com.github.josh910830.portablemq.consumer.deadletter.Broker
import com.github.josh910830.portablemq.consumer.deadletter.Broker.SPRING
import com.github.josh910830.portablemq.consumer.deadletter.Deadletter
import java.util.*

fun deadletterFixture(): Deadletter {
    return deadletterFixture(SPRING)
}

fun deadletterFixture(broker: Broker): Deadletter {
    val id = UUID.randomUUID().toString()
    val message = messageFixture()
    return Deadletter(id, message, broker)
}
