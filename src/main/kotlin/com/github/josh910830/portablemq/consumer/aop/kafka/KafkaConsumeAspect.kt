package com.github.josh910830.portablemq.consumer.aop.kafka

import com.github.josh910830.portablemq.consumer.ConsumeProcessor
import com.github.josh910830.portablemq.consumer.aop.Consume
import com.github.josh910830.portablemq.consumer.aop.ConsumerGroupParser
import com.github.josh910830.portablemq.consumer.deadletter.Broker.SPRING
import com.github.josh910830.portablemq.message.Message
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Aspect
@Component
class KafkaConsumeAspect(
    private val consumerGroupParser: ConsumerGroupParser,
    private val kafkaConsumerResolver: KafkaConsumerResolver,
    private val consumeProcessor: ConsumeProcessor
) {

    @Around("@annotation(a) && @annotation(b) && args(data,..)")
    fun consume(
        joinPoint: ProceedingJoinPoint,
        a: KafkaListener, b: Consume,
        data: String
    ) {
        val consumer = joinPoint.`this`
        val parseMethod = kafkaConsumerResolver.getParseMethod(consumer)
        val handleMethod = kafkaConsumerResolver.getHandleMethod(consumer)

        val message = parseMethod.invoke(consumer, data) as Message // TODO badletter

        consumeProcessor.consume(
            { handleMethod.invoke(consumer, message) },
            a.topics.first(), message,
            b.useConsumptionLog, consumerGroupParser.parse(a),
            b.useDeadletter, SPRING
        )

        joinPoint.proceed()
    }

}
