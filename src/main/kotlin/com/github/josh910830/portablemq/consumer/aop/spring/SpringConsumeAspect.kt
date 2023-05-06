package com.github.josh910830.portablemq.consumer.aop.spring

import com.github.josh910830.portablemq.core.consumer.Broker.SPRING
import com.github.josh910830.portablemq.core.consumer.Consume
import com.github.josh910830.portablemq.core.consumer.ConsumeProcessor
import com.github.josh910830.portablemq.core.message.Message
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Aspect
@Component
class SpringConsumeAspect(
    private val consumeProcessor: ConsumeProcessor,
    @Value("\${spring.kafka.consumer.group-id}") private val defaultGroupId: String
) {

    @Around("@annotation(a) && @annotation(b) && args(message)")
    fun consume(
        joinPoint: ProceedingJoinPoint,
        a: SpringListener, b: Consume,
        message: Message
    ) {
        val groupId = if (a.groupId == "") defaultGroupId else a.groupId

        consumeProcessor.consume(
            { joinPoint.proceed() },
            a.topic, message,
            b.useConsumptionLog, groupId,
            b.useDeadletter, SPRING
        )
    }

}
