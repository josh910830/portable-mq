package com.github.josh910830.portablemq.consumer.aop

import com.github.josh910830.portablemq.EnablePortableMQ
import com.github.josh910830.portablemq.tests.example.ExampleConfiguration
import com.github.josh910830.portablemq.tests.example.ExampleSpringConsumer
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
class ConsumeAspectTest(
    @Autowired val exampleSpringConsumer: ExampleSpringConsumer,
    @SpykBean val consumeAspect: ConsumeAspect
) : DescribeSpec({

    describe("consume") {
        context("on client") {
            it("aspect consume") {
                exampleSpringConsumer.consume(messageFixture())

                verify { consumeAspect.consume(any(), any(), any()) }
            }
        }
    }

})
