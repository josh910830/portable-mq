package com.github.josh910830.portablemq.consumer.log

interface ConsumptionLogStore {

    fun save(consumptionLog: ConsumptionLog)
    fun exists(consumerGroup: String, messageId: String): Boolean

}
