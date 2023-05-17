package com.github.josh910830.portablemq.support.template

import com.github.josh910830.portablemq.core.consumer.deadletter.Deadletter
import com.github.josh910830.portablemq.core.consumer.deadletter.interfaces.DeadletterNotifier

abstract class DeadletterLinkNotifier : DeadletterNotifier {

    final override fun notify(deadletter: Deadletter, redriveToken: String, exception: Exception) {
        val redriveLink = "$serverAddress/portable-mq/deadletter/redrive-token" +
                "?deadletterId=${deadletter.id}" +
                "&redriveToken=${redriveToken}"
        doNotify(deadletter, exception, redriveLink)
    }

    protected abstract val serverAddress: String
    protected abstract fun doNotify(deadletter: Deadletter, exception: Exception, redriveLink: String)

}
