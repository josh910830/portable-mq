package com.github.josh910830.portablemq.consumer.deadletter

import com.github.josh910830.portablemq.message.Message
import org.springframework.stereotype.Component
import java.util.*

@Component
class DeadletterHandler(
    private val deadletterStore: DeadletterStore,
    private val redriveTokenManager: RedriveTokenManager,
    private val deadletterNotifier: DeadletterNotifier
) {

    fun create(message: Message, broker: Broker) {
        val deadletterId = UUID.randomUUID().toString()
        val deadletter = Deadletter(deadletterId, message, broker)

        deadletterStore.save(deadletter)
        val redriveToken = redriveTokenManager.issue(deadletter.id)
        deadletterNotifier.notify(deadletter, redriveToken)
    }

}
