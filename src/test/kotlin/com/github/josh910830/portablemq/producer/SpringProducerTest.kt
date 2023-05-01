package com.github.josh910830.portablemq.producer

import com.github.josh910830.portablemq.message.IdentifiableMessage
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.ApplicationEventPublisher

class SpringProducerTest : DescribeSpec({
    val exampleProducer = ExampleProducer()

    describe("produce") {
        context("after initialize holder") {
            val applicationEventPublisher = mockk<ApplicationEventPublisher>(relaxed = true)
            ApplicationEventPublisherHolder(applicationEventPublisher).initialize()

            it("publish event") {
                val m = ExampleMessage("content")
                exampleProducer.produce(m)

                verify { applicationEventPublisher.publishEvent(m) }
            }
        }

    }

}) {
    data class ExampleMessage(
        val content: String
    ) : IdentifiableMessage()

    class ExampleProducer : SpringProducer<ExampleMessage>
}
