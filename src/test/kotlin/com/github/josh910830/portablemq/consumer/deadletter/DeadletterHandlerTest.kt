package com.github.josh910830.portablemq.consumer.deadletter

import com.github.josh910830.portablemq.consumer.deadletter.Broker.SPRING
import com.github.josh910830.portablemq.tests.example.ExampleDeadletterNotifier
import com.github.josh910830.portablemq.tests.example.ExampleDeadletterStore
import com.github.josh910830.portablemq.tests.example.ExampleRedriveTokenManager
import com.github.josh910830.portablemq.tests.fixture.messageFixture
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.spyk
import io.mockk.verify

class DeadletterHandlerTest : DescribeSpec({

    val deadletterStore = spyk(ExampleDeadletterStore())
    val redriveTokenManager = spyk(ExampleRedriveTokenManager())
    val deadletterNotifier = spyk(ExampleDeadletterNotifier())
    val deadletterHandler = DeadletterHandler(deadletterStore, redriveTokenManager, deadletterNotifier)

    describe("create") {
        it("save issue notify") {
            deadletterHandler.create(messageFixture(), SPRING)

            verify { deadletterStore.save(any()) }
            verify { redriveTokenManager.issue(any()) }
            verify { deadletterNotifier.notify(any(), any()) }
        }
    }

})
