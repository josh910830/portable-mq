package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.kafka.consumer.badletter.Badletter
import com.github.josh910830.portablemq.kafka.consumer.badletter.BadletterNotifier
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ExampleBadletterNotifier : BadletterNotifier {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun notify(badletter: Badletter, exception: Exception) {
        log.info("badletter: $badletter, exception: $exception")
    }

}
