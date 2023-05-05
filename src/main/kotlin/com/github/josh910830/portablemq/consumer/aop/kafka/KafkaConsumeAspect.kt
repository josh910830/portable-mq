package com.github.josh910830.portablemq.consumer.aop.kafka

import com.github.josh910830.portablemq.consumer.ConsumeProcessor
import com.github.josh910830.portablemq.consumer.aop.Consume
import com.github.josh910830.portablemq.consumer.aop.ConsumerGroupParser
import com.github.josh910830.portablemq.consumer.deadletter.Broker.SPRING
import com.github.josh910830.portablemq.message.kafka.KafkaDefaultMessage
import com.github.josh910830.portablemq.producer.kafka.ObjectMapperHolder
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Aspect
@Component
class KafkaConsumeAspect(
    private val consumerGroupParser: ConsumerGroupParser,
    private val consumeProcessor: ConsumeProcessor
) {

    @Around("@annotation(a) && @annotation(b) && args(data,..)")
    fun consume(
        joinPoint: ProceedingJoinPoint,
        a: KafkaListener, b: Consume,
        data: String
    ) {
        if (a.topics.size != 1) throw RuntimeException("@KafkaListener.topics should have 1 element.") // TODO postConstruct

        val message = ObjectMapperHolder.get().readValue(data, KafkaDefaultMessage::class.java)
        // TODO badletter

        consumeProcessor.consume(
            { joinPoint.proceed() },
            a.topics.first(), message,
            b.useConsumptionLog, consumerGroupParser.parse(a),
            b.useDeadletter, SPRING
        )
    }

}
