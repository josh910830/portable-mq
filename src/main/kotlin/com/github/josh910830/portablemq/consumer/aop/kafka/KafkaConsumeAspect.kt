package com.github.josh910830.portablemq.consumer.aop.kafka

import com.fasterxml.jackson.databind.ObjectMapper
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
import java.lang.reflect.Method
import java.util.function.Consumer
import java.util.function.Supplier

@Aspect
@Component
class KafkaConsumeAspect(
    private val consumerGroupParser: ConsumerGroupParser,
    private val kafkaConsumerResolver: KafkaConsumerResolver,
    private val objectMapper: ObjectMapper,
    private val consumeProcessor: ConsumeProcessor
) {

    @Around("@annotation(a) && @annotation(b) && args(data,..)")
    fun consume(
        joinPoint: ProceedingJoinPoint,
        a: KafkaListener, b: Consume,
        data: String
    ) {
        val consumer = joinPoint.`this`!!
        val parseMethod = kafkaConsumerResolver.getParseMethod(consumer)
        val handleMethod = kafkaConsumerResolver.getHandleMethod(consumer)

        optionalGet(
            { parse(parseMethod, consumer, data, handleMethod) },
            { it.printStackTrace() } // TODO badletter
        )?.let { message ->
            consumeProcessor.consume(
                { handleMethod.invoke(consumer, message) },
                a.topics.first(), message,
                b.useConsumptionLog, consumerGroupParser.parse(a),
                b.useDeadletter, SPRING
            )
            joinPoint.proceed()
        }
    }

    private fun parse(
        parseMethod: Method?, consumer: Any, data: String, handleMethod: Method
    ): Message {
        if (parseMethod != null) return parseMethod.invoke(consumer, data) as Message
        return objectMapper.readValue(data, handleMethod.parameterTypes[0]) as Message
    }


    private fun <T> optionalGet(supplier: Supplier<T>, onFail: Consumer<Exception>): T? {
        return try {
            supplier.get()
        } catch (e: Exception) {
            onFail.accept(e)
            null
        }
    }

}
