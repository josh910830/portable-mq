package com.github.josh910830.portablemq.message

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import io.mockk.every
import io.mockk.spyk

class IdentifiableMessageTest : DescribeSpec({

    describe("equals") {

        context("same id && different content") {
            it("is true") {
                val m1 = ExampleMessage("content 1")

                val m2 = spyk(ExampleMessage("content 2"))
                every { m2.id } answers { m1.id }

                m1 shouldBeEqual m2
            }
        }

        context("different id && same content") {
            it("is false") {
                val m1 = ExampleMessage("content")
                val m2 = ExampleMessage("content")

                m1 shouldNotBeEqual m2
            }
        }

    }

}) {
    data class ExampleMessage(
        val content: String
    ) : IdentifiableMessage()
}
