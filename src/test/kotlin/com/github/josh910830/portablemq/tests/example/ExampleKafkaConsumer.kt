package com.github.josh910830.portablemq.tests.example

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.josh910830.portablemq.consumer.Consumer
import com.github.josh910830.portablemq.consumer.aop.Consume
import com.github.josh910830.portablemq.consumer.aop.kafka.Handle
import com.github.josh910830.portablemq.consumer.aop.kafka.Parse
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener

@Consumer
class ExampleKafkaConsumer(
    private val objectMapper: ObjectMapper
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Consume
    @KafkaListener(topics = ["example-message"])
    fun consume(data: String) {
        log.info("commit data: $data")
    }

    @Parse // TODO default
    fun parse(data: String): ExampleMessage {
        log.info("parse data: $data")
        return objectMapper.readValue(data, ExampleMessage::class.java)
    }

    @Handle
    fun handle(message: ExampleMessage) {
        log.info("handle message: $message")
    }

}
