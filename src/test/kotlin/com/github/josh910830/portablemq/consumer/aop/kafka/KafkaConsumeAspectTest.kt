package com.github.josh910830.portablemq.consumer.aop.kafka

import com.github.josh910830.portablemq.EnablePortableMQ
import com.github.josh910830.portablemq.kafka.consumer.KafkaConsumeAspect
import com.github.josh910830.portablemq.kafka.consumer.badletter.BadletterHandler
import com.github.josh910830.portablemq.kafka.producer.ObjectMapperHolder
import com.github.josh910830.portablemq.tests.example.ExampleConfiguration
import com.github.josh910830.portablemq.tests.example.ExampleKafkaConsumer
import com.github.josh910830.portablemq.tests.fixture.messageFixture
import com.ninjasquad.springmockk.SpykBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@EnablePortableMQ
@Import(ExampleConfiguration::class)
class KafkaConsumeAspectTest(
    @Autowired val exampleKafkaConsumer: ExampleKafkaConsumer,
    @SpykBean val kafkaConsumerAspect: KafkaConsumeAspect,
    @SpykBean val badletterHandler: BadletterHandler
) : DescribeSpec({

    describe("consume") {
        context("on client") {
            val data = ObjectMapperHolder.get().writeValueAsString(messageFixture())
            it("aspect consume") {
                exampleKafkaConsumer.consume(data)

                verify { kafkaConsumerAspect.consume(any(), any(), any(), any()) }
            }
        }

        context("invalid data") {
            val data = "invalid"
            it("badletter") {
                exampleKafkaConsumer.consume(data)

                verify { badletterHandler.create(any(), any(), any(), any()) }
            }
        }
    }

})
