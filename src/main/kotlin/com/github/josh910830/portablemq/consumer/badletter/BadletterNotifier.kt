package com.github.josh910830.portablemq.consumer.badletter

interface BadletterNotifier {

    fun notify(badletter: Badletter, exception: Exception)

}
