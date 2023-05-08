package com.github.josh910830.portablemq.support.autoconfiguration.log

import com.github.josh910830.portablemq.kafka.consumer.badletter.Badletter
import com.github.josh910830.portablemq.kafka.consumer.badletter.BadletterNotifier
import com.github.josh910830.portablemq.support.autoconfiguration.DefaultBeanClass

class LogBadletterNotifier : DefaultBeanClass(), BadletterNotifier {

    override fun notify(badletter: Badletter, exception: Exception) {
        log.info(
            "\n" +
                "\tbadletter: $badletter\n" +
                "\texception: $exception"
        )
    }

}
