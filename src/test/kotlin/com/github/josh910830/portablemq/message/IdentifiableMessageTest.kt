package com.github.josh910830.portablemq.message

import com.github.josh910830.portablemq.tests.example.ExampleMessage
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.spyk

class IdentifiableMessageTest : DescribeSpec({

    describe("equals") {

        context("same id && different content") {
            val m1 = ExampleMessage("content 1")
            val m2 = spyk(ExampleMessage("content 2"))
            every { m2.id } answers { m1.id }

            it("true") {
                val equals = m1 == m2
                equals shouldBe true
            }
        }

        context("different id && same content") {
            val m1 = ExampleMessage("content")
            val m2 = ExampleMessage("content")

            it("false") {
                val equals = m1 == m2
                equals shouldBe false
            }
        }

    }

})
