package com.github.josh910830.portablemq.kafka.consumer.badletter

interface BadletterStore {

    fun save(badletter: Badletter)

}
