package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.consumer.aop.Consume
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.TestComponent
import org.springframework.context.event.EventListener

@TestComponent
class ExampleSpringConsumer {

    private val log = LoggerFactory.getLogger(javaClass)

    @EventListener
    @Consume
    fun consume(exampleMessage: ExampleMessage) {
        log.info("exampleMessage: $exampleMessage")
    }

}
