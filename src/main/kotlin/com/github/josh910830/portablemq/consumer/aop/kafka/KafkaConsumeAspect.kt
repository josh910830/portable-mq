package com.github.josh910830.portablemq.consumer.aop.kafka

import com.github.josh910830.portablemq.PortableMQProperties
import com.github.josh910830.portablemq.consumer.ConsumeProcessor
import com.github.josh910830.portablemq.consumer.aop.Consume
import com.github.josh910830.portablemq.consumer.deadletter.Broker.SPRING
import com.github.josh910830.portablemq.message.kafka.KafkaDefaultMessage
import com.github.josh910830.portablemq.producer.kafka.ObjectMapperHolder
import com.github.josh910830.portablemq.utility.Extracts.Companion.extractGroupId
import com.github.josh910830.portablemq.utility.Extracts.Companion.extractTopic
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Aspect
@Component
class KafkaConsumeAspect(
    private val portableMQProperties: PortableMQProperties,
    private val consumeProcessor: ConsumeProcessor
) {

    @Around("@annotation(a) && @annotation(b) && args(data,..)")
    fun consume(
        joinPoint: ProceedingJoinPoint,
        a: KafkaListener, b: Consume,
        data: String
    ) {
        val groupId = extractGroupId(a, portableMQProperties)
        val topic = extractTopic(a)

        val message = ObjectMapperHolder.get().readValue(data, KafkaDefaultMessage::class.java)
        // TODO badletter

        consumeProcessor.consume(
            { joinPoint.proceed() },
            topic, message,
            b.useConsumptionLog, groupId,
            b.useDeadletter, SPRING
        )
    }

}
