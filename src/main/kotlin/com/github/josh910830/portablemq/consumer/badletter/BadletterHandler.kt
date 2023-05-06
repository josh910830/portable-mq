package com.github.josh910830.portablemq.consumer.badletter

import com.github.josh910830.portablemq.core.consumer.Broker
import org.springframework.stereotype.Component
import java.util.*

@Component
class BadletterHandler(
    private val badletterStore: BadletterStore,
    private val badletterNotifier: BadletterNotifier
) {

    fun create(topic: String, data: String, broker: Broker, exception: Exception) {
        val badletterId = UUID.randomUUID().toString()
        val badletter = Badletter(badletterId, topic, data, broker)

        badletterStore.save(badletter)
        badletterNotifier.notify(badletter, exception)
    }

}
