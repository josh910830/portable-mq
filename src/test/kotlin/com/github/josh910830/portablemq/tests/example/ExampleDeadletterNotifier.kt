package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.consumer.deadletter.Deadletter
import com.github.josh910830.portablemq.consumer.deadletter.DeadletterNotifier
import org.slf4j.LoggerFactory

class ExampleDeadletterNotifier : DeadletterNotifier {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun notify(deadletter: Deadletter, redriveToken: String) {
        log.info("deadletter: $deadletter, redriveToken: $redriveToken");
    }

}
