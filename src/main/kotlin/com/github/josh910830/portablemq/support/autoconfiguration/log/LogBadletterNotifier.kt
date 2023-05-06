package com.github.josh910830.portablemq.support.autoconfiguration.log

import com.github.josh910830.portablemq.kafka.consumer.badletter.Badletter
import com.github.josh910830.portablemq.kafka.consumer.badletter.BadletterNotifier
import com.github.josh910830.portablemq.support.autoconfiguration.DefaultBeanClass
import org.slf4j.LoggerFactory

class LogBadletterNotifier : DefaultBeanClass(), BadletterNotifier {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun notify(badletter: Badletter, exception: Exception) {
        log.warn(
            "\n" +
                "\tbadletter: $badletter\n" +
                "\texception: $exception"
        )
    }

}
