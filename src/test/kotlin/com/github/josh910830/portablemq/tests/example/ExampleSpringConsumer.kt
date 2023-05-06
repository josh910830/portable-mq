package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.core.consumer.Consumer
import com.github.josh910830.portablemq.core.consumer.Consume
import com.github.josh910830.portablemq.spring.consumer.SpringListener
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
