package com.github.josh910830.portablemq.consumer.deadletter

interface DeadletterNotifier {

    fun notify(deadletter: Deadletter, redriveToken: String, exception: Exception)

}
