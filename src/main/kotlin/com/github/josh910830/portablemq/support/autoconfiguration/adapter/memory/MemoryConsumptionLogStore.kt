package com.github.josh910830.portablemq.support.autoconfiguration.adapter.memory

import com.github.josh910830.portablemq.core.consumer.log.ConsumptionLog
import com.github.josh910830.portablemq.core.consumer.log.interfaces.ConsumptionLogStore
import com.github.josh910830.portablemq.support.autoconfiguration.adapter.DefaultBeanClass

class MemoryConsumptionLogStore : DefaultBeanClass(), ConsumptionLogStore {

    private val map = mutableMapOf<String, ConsumptionLog>()

    private val delimiter = "::"


    override fun save(consumptionLog: ConsumptionLog) {
        log.debug("save($consumptionLog)")
        val key = "${consumptionLog.groupId}$delimiter${consumptionLog.message.id}"
        map[key] = consumptionLog
    }

    override fun exists(groupId: String, messageId: String): Boolean {
        log.debug("exists($groupId, $messageId)")
        val key = "$groupId$delimiter$messageId"
        return map.contains(key)
    }

}
