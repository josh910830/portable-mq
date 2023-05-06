package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.kafka.consumer.badletter.Badletter
import com.github.josh910830.portablemq.kafka.consumer.badletter.BadletterStore
import org.springframework.stereotype.Component

@Component
class ExampleBadletterStore : BadletterStore {

    private val map = mutableMapOf<String, Badletter>()

    override fun save(badletter: Badletter) {
        map[badletter.id] = badletter
    }

}
