package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.core.consumer.log.ConsumptionLog
import com.github.josh910830.portablemq.core.consumer.log.ConsumptionLogStore
import org.springframework.boot.test.context.TestComponent

@TestComponent
class ExampleConsumptionLogStore : ConsumptionLogStore {

    private val set = mutableSetOf<String>()

    override fun save(consumptionLog: ConsumptionLog) {
        val key = "${consumptionLog.groupId}-${consumptionLog.message.id}"
        set.add(key)
    }

    override fun exists(groupId: String, messageId: String): Boolean {
        val key = "$groupId-$messageId"
        return set.contains(key)
    }

}
