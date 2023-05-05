package com.github.josh910830.portablemq.consumer.aop

import com.github.josh910830.portablemq.PortableMQProperties
import com.github.josh910830.portablemq.consumer.ConsumeProcessor
import com.github.josh910830.portablemq.consumer.aop.BrokerType.DEFAULT
import com.github.josh910830.portablemq.message.Message
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class ConsumeAspect(
    private val portableMQProperties: PortableMQProperties,
    private val consumeProcessor: ConsumeProcessor
) {

    @Around("@annotation(consume) && args(message,..)")
    fun consume(joinPoint: ProceedingJoinPoint, consume: Consume, message: Message) {
        val default = portableMQProperties.default
        val consumerGroup = if (consume.consumerGroup == "default") default.consumerGroup else consume.consumerGroup
        val broker = if (consume.brokerType == DEFAULT) default.broker else consume.brokerType.toBroker()

        consumeProcessor.consume(
            { joinPoint.proceed() },
            message,
            consume.useConsumptionLog, consumerGroup,
            consume.useDeadletter, broker
        )
    }

}
