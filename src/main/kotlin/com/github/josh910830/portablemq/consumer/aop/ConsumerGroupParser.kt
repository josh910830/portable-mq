package com.github.josh910830.portablemq.consumer.aop

import com.github.josh910830.portablemq.consumer.aop.spring.SpringListener
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ConsumerGroupParser(
    @Value("\${spring.kafka.consumer.group-id}") private val defaultGroupId: String
) {

    fun parse(springListener: SpringListener): String {
        return parse(springListener.groupId)
    }

    fun parse(kafkaListener: KafkaListener): String {
        return parse(kafkaListener.groupId)
    }

    private fun parse(defined: String): String {
        return if (defined == "") defaultGroupId else defined
    }

}
