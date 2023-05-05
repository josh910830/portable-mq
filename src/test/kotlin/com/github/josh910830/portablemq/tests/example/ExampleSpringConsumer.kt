package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.consumer.Consumer
import com.github.josh910830.portablemq.consumer.aop.Consume
import com.github.josh910830.portablemq.consumer.aop.spring.SpringListener
import org.slf4j.LoggerFactory

@Consumer
class ExampleSpringConsumer {

    private val log = LoggerFactory.getLogger(javaClass)

    @Consume
    @SpringListener("example-message")
    fun consume(exampleMessage: ExampleMessage) {
        log.info("exampleMessage: $exampleMessage")
    }

}
