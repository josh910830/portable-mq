package com.github.josh910830.portablemq.consumer.aop.spring

import com.github.josh910830.portablemq.EnablePortableMQ
import com.github.josh910830.portablemq.spring.consumer.SpringConsumeAspect
import com.github.josh910830.portablemq.tests.example.ExampleSpringProducer
import com.github.josh910830.portablemq.tests.fixture.messageFixture
import com.ninjasquad.springmockk.SpykBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@EnablePortableMQ
class SpringConsumeAspectTest(
    @Autowired val exampleSpringProducer: ExampleSpringProducer,
    @SpykBean val springConsumeAspect: SpringConsumeAspect
) : DescribeSpec({

    describe("consume") {
        context("on produce") {
            val message = messageFixture()
            exampleSpringProducer.produce(message)
            it("via aspect") {
                verify { springConsumeAspect.consume(any(), any(), any(), message) }
            }
        }
    }

})
