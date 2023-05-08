package com.github.josh910830.portablemq.kafka.consumer.badletter.interfaces

import com.github.josh910830.portablemq.kafka.consumer.badletter.Badletter

interface BadletterNotifier {

    fun notify(badletter: Badletter, exception: Exception)

}
