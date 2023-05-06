package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.core.consumer.Consume
import com.github.josh910830.portablemq.core.consumer.Consumer
import com.github.josh910830.portablemq.kafka.consumer.Handle
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment

@Consumer
class ExampleKafkaConsumer {

    private val log = LoggerFactory.getLogger(javaClass)

    @Consume(useBadletter = true)
    @KafkaListener(topics = ["example-message"])
    fun consume(data: String, ack: Acknowledgment) {
        log.info("listen data: $data")
    }

    @Handle
    fun handle(message: ExampleMessage) {
        log.info("handle message: $message")
    }

}
