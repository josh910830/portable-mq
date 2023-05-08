package com.github.josh910830.portablemq.consumer.deadletter

import com.github.josh910830.portablemq.EnablePortableMQ
import com.github.josh910830.portablemq.core.consumer.Broker.SPRING
import com.github.josh910830.portablemq.core.consumer.deadletter.DeadletterService
import com.github.josh910830.portablemq.core.consumer.deadletter.DeadletterStore
import com.github.josh910830.portablemq.spring.consumer.deadletter.SpringRedriveProducer
import com.github.josh910830.portablemq.tests.fixture.deadletterFixture
import com.ninjasquad.springmockk.SpykBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@EnablePortableMQ
class DeadletterServiceTest(
    @Autowired val deadletterService: DeadletterService,
    @SpykBean val deadletterStore: DeadletterStore,
    @SpykBean val springRedriveProducer: SpringRedriveProducer
) : DescribeSpec({

    describe("redrive") {
        context("deadletter by spring") {
            val deadletter = deadletterFixture(SPRING)
            deadletterStore.save(deadletter)

            it("produce message") {
                deadletterService.redrive(deadletter.id)

                verify { springRedriveProducer.produce(any(), any()) }
            }
        }
    }

})
