package com.github.josh910830.portablemq.kafka.consumer.badletter.interfaces

import com.github.josh910830.portablemq.kafka.consumer.badletter.Badletter

interface BadletterStore {

    fun save(badletter: Badletter)
    fun findAll(): List<Badletter>
    fun findById(badletterId: String): Badletter
    fun deleteById(badletterId: String)
    fun clear()

}
