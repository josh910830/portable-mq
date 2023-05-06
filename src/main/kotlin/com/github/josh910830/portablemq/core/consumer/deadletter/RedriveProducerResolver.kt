package com.github.josh910830.portablemq.core.consumer.deadletter

import com.github.josh910830.portablemq.core.consumer.Broker
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class RedriveProducerResolver(
    private val producers: List<RedriveProducer>
) {

    private lateinit var map: Map<Broker, RedriveProducer>

    @PostConstruct
    fun initialize() {
        val tempMap = mutableMapOf<Broker, RedriveProducer>()
        producers.forEach { tempMap[it.broker] = it }
        Broker.values().forEach { tempMap[it] ?: throw IllegalStateException("Broker should have RedriveProducer") }
        map = tempMap
    }

    fun get(broker: Broker): RedriveProducer {
        return map[broker]!!
    }

}
