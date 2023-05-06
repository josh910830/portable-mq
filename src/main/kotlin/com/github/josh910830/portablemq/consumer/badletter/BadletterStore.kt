package com.github.josh910830.portablemq.consumer.badletter

interface BadletterStore {

    fun save(badletter: Badletter)

}
