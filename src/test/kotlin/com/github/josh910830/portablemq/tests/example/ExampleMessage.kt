package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.message.IdentifiableMessage

data class ExampleMessage(
    val content: String
) : IdentifiableMessage()
