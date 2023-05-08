package com.github.josh910830.portablemq.core.consumer

import com.github.josh910830.portablemq.core.consumer.deadletter.DeadletterHandler
import com.github.josh910830.portablemq.core.consumer.log.ConsumptionLoggingDecorator
import com.github.josh910830.portablemq.core.message.Message
import org.springframework.stereotype.Component

@Component
class ConsumeProcessor(
    private val consumptionLoggingDecorator: ConsumptionLoggingDecorator,
    private val deadletterHandler: DeadletterHandler
) {

    fun consume(
        action: Runnable,
        topic: String, message: Message,
        useConsumptionLog: Boolean, groupId: String,
        useDeadletter: Boolean, broker: Broker
    ) {
        try {
            if (useConsumptionLog) consumptionLoggingDecorator.consume(groupId, message, action)
            else action.run()
        } catch (e: Exception) {
            if (useDeadletter) deadletterHandler.create(topic, message, broker, e)
        }
    }

}
