package com.github.josh910830.portablemq.consumer.aop

import com.github.josh910830.portablemq.consumer.aop.BrokerType.DEFAULT
import com.github.josh910830.portablemq.consumer.deadletter.Broker
import com.github.josh910830.portablemq.consumer.deadletter.DeadletterHandler
import com.github.josh910830.portablemq.consumer.log.ConsumptionLogDecorator
import com.github.josh910830.portablemq.message.Message
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Aspect
@Component
class ConsumeAspect(
    private val consumptionLogDecorator: ConsumptionLogDecorator,
    private val deadletterHandler: DeadletterHandler,
    @Value("\${portable-mq.default.consumer-group}") private val defaultConsumeGroup: String,
    @Value("\${portable-mq.default.broker}") private val defaultBroker: Broker,
) {

    @Around("@annotation(consume) && args(message,..)")
    fun consume(joinPoint: ProceedingJoinPoint, consume: Consume, message: Message) {
        val consumerGroup = if (consume.consumerGroup == "default") defaultConsumeGroup else consume.consumerGroup
        val broker = if (consume.brokerType == DEFAULT) defaultBroker else consume.brokerType.toBroker()

        consume(
            { joinPoint.proceed() },
            message,
            consume.useConsumptionLog, consumerGroup,
            consume.useDeadletter, broker
        )
    }

    private fun consume(
        action: Runnable, message: Message,
        consumptionLog: Boolean, consumerGroup: String,
        deadletter: Boolean, broker: Broker
    ) {
        try {
            if (consumptionLog) consumptionLogDecorator.consume(consumerGroup, message, action)
            else action.run()
        } catch (e: Exception) {
            if (deadletter) deadletterHandler.create(message, broker, e)
        }
    }

}
