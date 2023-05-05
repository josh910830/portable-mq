package com.github.josh910830.portablemq.consumer

import com.github.josh910830.portablemq.consumer.deadletter.Broker
import com.github.josh910830.portablemq.consumer.deadletter.DeadletterHandler
import com.github.josh910830.portablemq.consumer.log.ConsumptionLogDecorator
import com.github.josh910830.portablemq.message.Message
import org.springframework.stereotype.Component

@Component
class ConsumeProcessor(
    private val consumptionLogDecorator: ConsumptionLogDecorator,
    private val deadletterHandler: DeadletterHandler
) {

    fun consume(
        action: Runnable,
        topic: String, message: Message,
        consumptionLog: Boolean, groupId: String,
        deadletter: Boolean, broker: Broker
    ) {
        try {
            if (consumptionLog) consumptionLogDecorator.consume(groupId, message, action)
            else action.run()
        } catch (e: Exception) {
            if (deadletter) deadletterHandler.create(topic, message, broker, e)
        }
    }

}
