package com.github.josh910830.portablemq.consumer.aop.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.josh910830.portablemq.consumer.badletter.BadletterHandler
import com.github.josh910830.portablemq.core.consumer.Broker.KAFKA
import com.github.josh910830.portablemq.core.consumer.Consume
import com.github.josh910830.portablemq.core.consumer.ConsumeProcessor
import com.github.josh910830.portablemq.core.message.Message
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import java.util.function.Consumer
import java.util.function.Supplier

@Aspect
@Component
class KafkaConsumeAspect(
    private val kafkaConsumerResolver: KafkaConsumerResolver,
    private val badletterHandler: BadletterHandler,
    private val consumeProcessor: ConsumeProcessor,
    private val objectMapper: ObjectMapper,
    @Value("\${spring.kafka.consumer.group-id}") private val defaultGroupId: String
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

        val topic = a.topics.first()
        val groupId = if (a.groupId == "") defaultGroupId else a.groupId

        optionalGet(
            { parse(parseMethod, consumer, data, handleMethod) },
            { if (b.useBadletter) badletterHandler.create(topic, data, KAFKA, it) }
        )?.let { message ->
            consumeProcessor.consume(
                { handleMethod.invoke(consumer, message) },
                topic, message,
                b.useConsumptionLog, groupId,
                b.useDeadletter, KAFKA
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
