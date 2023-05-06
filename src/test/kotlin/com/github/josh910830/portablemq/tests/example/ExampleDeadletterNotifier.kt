package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.core.consumer.deadletter.Deadletter
import com.github.josh910830.portablemq.core.consumer.deadletter.DeadletterNotifier
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.TestComponent

@TestComponent
class ExampleDeadletterNotifier : DeadletterNotifier {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun notify(deadletter: Deadletter, redriveToken: String, exception: Exception) {
        log.info("deadletter: $deadletter, redriveToken: $redriveToken, exception: $exception")
    }

}
