package com.github.josh910830.portablemq.producer

import com.github.josh910830.portablemq.producer.spring.ApplicationEventPublisherHolder
import com.github.josh910830.portablemq.tests.example.ExampleSpringProducer
import com.github.josh910830.portablemq.tests.fixture.messageFixture
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.ApplicationEventPublisher

class SpringProducerTest : DescribeSpec({
    val exampleProducer = ExampleSpringProducer()

    describe("produce") {
        context("holder is initialized") {
            val applicationEventPublisher = mockk<ApplicationEventPublisher>(relaxed = true)
            ApplicationEventPublisherHolder(applicationEventPublisher).initialize()

            it("publish event") {
                val m = messageFixture()
                exampleProducer.produce(m)

                verify { applicationEventPublisher.publishEvent(m) }
            }
        }

    }

})
