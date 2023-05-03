package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.consumer.log.ConsumptionLog
import com.github.josh910830.portablemq.consumer.log.ConsumptionLogStore

class ExampleConsumptionLogStore : ConsumptionLogStore {

    private val set = mutableSetOf<String>()

    override fun save(consumptionLog: ConsumptionLog) {
        val key = "${consumptionLog.consumerGroup}-${consumptionLog.message.id}"
        set.add(key)
    }

    override fun exists(consumerGroup: String, messageId: String): Boolean {
        val key = "$consumerGroup-$messageId"
        return set.contains(key)
    }

}
