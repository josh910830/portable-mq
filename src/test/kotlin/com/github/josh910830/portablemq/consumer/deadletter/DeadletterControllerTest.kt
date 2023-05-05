package com.github.josh910830.portablemq.consumer.deadletter

import com.github.josh910830.portablemq.EnablePortableMQ
import com.github.josh910830.portablemq.consumer.deadletter.Broker.SPRING
import com.github.josh910830.portablemq.producer.spring.SpringRedriveProducer
import com.github.josh910830.portablemq.tests.example.*
import com.github.josh910830.portablemq.tests.fixture.deadletterFixture
import com.ninjasquad.springmockk.SpykBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.stream.Collectors.joining

@SpringBootTest
@EnablePortableMQ
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@Import(ExampleConfiguration::class)
class DeadletterControllerTest(
    @Autowired val mockMvc: MockMvc,
    @SpykBean val deadletterStore: DeadletterStore,
    @SpykBean val redriveTokenManager: ExampleRedriveTokenManager,
    @SpykBean val springRedriveProducer: SpringRedriveProducer,
) : DescribeSpec({

    describe("redrive") {
        context("deadletters are exist") {
            val d1 = deadletterFixture(SPRING)
            val d2 = deadletterFixture(SPRING)
            val d3 = deadletterFixture(SPRING)
            deadletterStore.save(d1)
            deadletterStore.save(d2)
            deadletterStore.save(d3)

            it("produce selected") {
                val targets = listOf(d1, d2)
                val deadletterIds = targets.map { it.id }.stream().collect(joining(","))

                mockMvc.post("/_deadletter/redrive?deadletterIds=$deadletterIds")
                    .andExpect { status { isOk() } }

                verify(exactly = 2) { springRedriveProducer.produce(any(), any()) }
            }
        }
    }

    describe("redrive all") {
        context("partial already redriven") {
            val d1 = deadletterFixture(SPRING)
            val d2 = deadletterFixture(SPRING)
            d1.redriven = true
            deadletterStore.save(d1)
            deadletterStore.save(d2)

            it("produce rest") {
                mockMvc.post("/_deadletter/redrive-all")
                    .andExpect { status { isOk() } }

                verify(exactly = 1) { springRedriveProducer.produce(any(), any()) }
            }
        }
    }

    describe("redrive with token") {
        val deadletter = deadletterFixture(SPRING)
        deadletterStore.save(deadletter)

        context("valid") {
            val redriveToken = redriveTokenManager.issue(deadletter.id)

            it("success") {
                mockMvc.get("/_deadletter/redrive-token?deadletterId=${deadletter.id}&redriveToken=$redriveToken")
                    .andExpect { status { isOk() } }
            }
        }

        context("invalid") {
            val redriveToken = "invalid"

            it("fail") {
                mockMvc.get("/_deadletter/redrive-token?deadletterId=${deadletter.id}&redriveToken=$redriveToken")
                    .andExpect { status { isBadRequest() } }
            }
        }

    }

})
