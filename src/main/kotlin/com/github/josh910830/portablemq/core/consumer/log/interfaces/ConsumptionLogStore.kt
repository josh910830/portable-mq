package com.github.josh910830.portablemq.core.consumer.log.interfaces

import com.github.josh910830.portablemq.core.consumer.log.ConsumptionLog

interface ConsumptionLogStore {

    fun save(consumptionLog: ConsumptionLog)
    fun exists(groupId: String, messageId: String): Boolean

}
