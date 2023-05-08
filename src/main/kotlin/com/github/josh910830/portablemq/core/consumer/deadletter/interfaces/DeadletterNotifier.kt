package com.github.josh910830.portablemq.core.consumer.deadletter.interfaces

import com.github.josh910830.portablemq.core.consumer.deadletter.Deadletter

interface DeadletterNotifier {

    fun notify(deadletter: Deadletter, redriveToken: String, exception: Exception)

}
