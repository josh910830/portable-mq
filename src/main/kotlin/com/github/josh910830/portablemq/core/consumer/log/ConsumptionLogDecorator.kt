package com.github.josh910830.portablemq.core.consumer.log

import com.github.josh910830.portablemq.core.message.Message
import org.springframework.stereotype.Component

@Component
class ConsumptionLogDecorator(
    private val consumptionLogStore: ConsumptionLogStore
) {

    fun consume(groupId: String, message: Message, action: Runnable) {
        if (consumptionLogStore.exists(groupId, message.id)) return

        action.run()

        val consumptionLog = ConsumptionLog(groupId, message)
        consumptionLogStore.save(consumptionLog)
    }

}
