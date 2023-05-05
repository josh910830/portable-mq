package com.github.josh910830.portablemq.consumer.aop.spring

import com.github.josh910830.portablemq.PortableMQProperties
import com.github.josh910830.portablemq.consumer.ConsumeProcessor
import com.github.josh910830.portablemq.consumer.deadletter.Broker.SPRING
import com.github.josh910830.portablemq.message.Message
import com.github.josh910830.portablemq.utility.Extracts.Companion.extractGroupId
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class SpringConsumeAspect(
    private val portableMQProperties: PortableMQProperties,
    private val consumeProcessor: ConsumeProcessor
) {

    @Around("@annotation(a) && args(message)")
    fun consume(joinPoint: ProceedingJoinPoint, a: SpringListener, message: Message) {
        val groupId = extractGroupId(a, portableMQProperties)

        consumeProcessor.consume(
            { joinPoint.proceed() },
            message,
            a.useConsumptionLog, groupId,
            a.useDeadletter, SPRING
        )
    }

}
