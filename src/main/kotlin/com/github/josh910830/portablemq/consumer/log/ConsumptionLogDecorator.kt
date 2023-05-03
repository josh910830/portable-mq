package com.github.josh910830.portablemq.consumer.log

import com.github.josh910830.portablemq.message.Message
import org.springframework.stereotype.Component

@Component
class ConsumptionLogDecorator(
    private val consumptionLogStore: ConsumptionLogStore
) {

    fun consume(consumerGroup: String, message: Message, action: Runnable) {
        if (consumptionLogStore.exists(consumerGroup, message.id)) return

        action.run()

        val consumptionLog = ConsumptionLog(consumerGroup, message)
        consumptionLogStore.save(consumptionLog)
    }

}
