package com.github.josh910830.portablemq.consumer.aop.kafka

import com.github.josh910830.portablemq.EnablePortableMQ
import com.github.josh910830.portablemq.producer.kafka.ObjectMapperHolder
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
    @SpykBean val kafkaConsumerAspect: KafkaConsumeAspect
) : DescribeSpec({

    describe("consume") {
        context("on client") {
            it("aspect consume") {
                val message = messageFixture()
                val data = ObjectMapperHolder.get().writeValueAsString(message)
                exampleKafkaConsumer.consume(data)

                verify { kafkaConsumerAspect.consume(any(), any(), any(), any()) }
            }
        }
    }

})
