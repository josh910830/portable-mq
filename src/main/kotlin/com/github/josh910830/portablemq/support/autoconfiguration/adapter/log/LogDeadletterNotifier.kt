package com.github.josh910830.portablemq.support.autoconfiguration.adapter.log

import com.github.josh910830.portablemq.core.consumer.deadletter.Deadletter
import com.github.josh910830.portablemq.core.consumer.deadletter.interfaces.DeadletterNotifier
import com.github.josh910830.portablemq.support.autoconfiguration.adapter.DefaultBeanClass

class LogDeadletterNotifier : DefaultBeanClass(), DeadletterNotifier {

    override fun notify(deadletter: Deadletter, redriveToken: String, exception: Exception) {
        log.info(
            "\n" +
                "\tdeadletter: $deadletter\n" +
                "\tredriveToken: $redriveToken\n" +
                "\texception: $exception"
        )
    }

}
