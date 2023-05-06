package com.github.josh910830.portablemq.support.autoconfiguration.log

import com.github.josh910830.portablemq.core.consumer.deadletter.Deadletter
import com.github.josh910830.portablemq.core.consumer.deadletter.DeadletterNotifier
import com.github.josh910830.portablemq.support.autoconfiguration.DefaultBeanClass
import org.slf4j.LoggerFactory

class LogDeadletterNotifier : DefaultBeanClass(), DeadletterNotifier {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun notify(deadletter: Deadletter, redriveToken: String, exception: Exception) {
        log.warn(
            "\n" +
                "\tdeadletter: $deadletter\n" +
                "\tredriveToken: $redriveToken\n" +
                "\texception: $exception"
        )
    }

}
