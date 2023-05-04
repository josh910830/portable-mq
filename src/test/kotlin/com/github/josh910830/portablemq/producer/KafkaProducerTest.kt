package com.github.josh910830.portablemq.producer

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.josh910830.portablemq.producer.kafka.KafkaTemplateHolder
import com.github.josh910830.portablemq.producer.kafka.ObjectMapperHolder
import com.github.josh910830.portablemq.tests.example.ExampleKafkaProducer
import com.github.josh910830.portablemq.tests.fixture.messageFixture
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk
import io.mockk.verify
import org.springframework.kafka.core.KafkaTemplate

class KafkaProducerTest : DescribeSpec({
    val exampleProducer = ExampleKafkaProducer()

    describe("produce") {
        context("holder is initialized") {
            ObjectMapperHolder(ObjectMapper()).initialize()

            val kafkaTemplate = mockk<KafkaTemplate<String, String>>(relaxed = true)
            KafkaTemplateHolder(kafkaTemplate).initialize()

            it("send data") {
                val m = messageFixture()
                exampleProducer.produce(m)

                verify { kafkaTemplate.send(any(), any()) }
            }
        }

    }

})
