package com.github.josh910830.portablemq.consumer.deadletter

import com.github.josh910830.portablemq.EnablePortableMQ
import com.github.josh910830.portablemq.core.consumer.Broker.SPRING
import com.github.josh910830.portablemq.core.consumer.deadletter.DeadletterHandler
import com.github.josh910830.portablemq.core.consumer.deadletter.DeadletterNotifier
import com.github.josh910830.portablemq.core.consumer.deadletter.DeadletterStore
import com.github.josh910830.portablemq.core.consumer.deadletter.RedriveTokenManager
import com.github.josh910830.portablemq.tests.fixture.messageFixture
import com.ninjasquad.springmockk.SpykBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@EnablePortableMQ
class DeadletterHandlerTest(
    @Autowired val deadletterHandler: DeadletterHandler,
    @SpykBean val deadletterStore: DeadletterStore,
    @SpykBean val redriveTokenManager: RedriveTokenManager,
    @SpykBean val deadletterNotifier: DeadletterNotifier,
) : DescribeSpec({

    describe("create") {
        it("save issue notify") {
            deadletterHandler.create("topic", messageFixture(), SPRING, RuntimeException())

            verify { deadletterStore.save(any()) }
            verify { redriveTokenManager.issue(any()) }
            verify { deadletterNotifier.notify(any(), any(), any()) }
        }
    }

})
