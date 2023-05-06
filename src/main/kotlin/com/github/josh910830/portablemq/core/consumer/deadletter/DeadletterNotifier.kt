package com.github.josh910830.portablemq.core.consumer.deadletter

interface DeadletterNotifier {

    fun notify(deadletter: Deadletter, redriveToken: String, exception: Exception)

}
