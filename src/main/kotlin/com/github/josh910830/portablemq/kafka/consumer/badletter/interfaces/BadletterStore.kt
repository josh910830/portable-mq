package com.github.josh910830.portablemq.kafka.consumer.badletter.interfaces

import com.github.josh910830.portablemq.kafka.consumer.badletter.Badletter

interface BadletterStore {

    fun save(badletter: Badletter)

}
