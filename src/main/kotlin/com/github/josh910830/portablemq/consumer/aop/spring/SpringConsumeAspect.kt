package com.github.josh910830.portablemq.consumer.aop.spring

import com.github.josh910830.portablemq.consumer.Broker.SPRING
import com.github.josh910830.portablemq.consumer.ConsumeProcessor
import com.github.josh910830.portablemq.consumer.aop.Consume
import com.github.josh910830.portablemq.consumer.aop.ConsumerGroupParser
import com.github.josh910830.portablemq.message.Message
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class SpringConsumeAspect(
    private val consumerGroupParser: ConsumerGroupParser,
    private val consumeProcessor: ConsumeProcessor
) {

    @Around("@annotation(a) && @annotation(b) && args(message)")
    fun consume(
        joinPoint: ProceedingJoinPoint,
        a: SpringListener, b: Consume,
        message: Message
    ) {
        consumeProcessor.consume(
            { joinPoint.proceed() },
            a.topic, message,
            b.useConsumptionLog, consumerGroupParser.parse(a),
            b.useDeadletter, SPRING
        )
    }

}
