package com.github.josh910830.portablemq.consumer.log

import com.github.josh910830.portablemq.EnablePortableMQ
import com.github.josh910830.portablemq.core.consumer.log.ConsumptionLog
import com.github.josh910830.portablemq.core.consumer.log.ConsumptionLoggingDecorator
import com.github.josh910830.portablemq.core.consumer.log.interfaces.ConsumptionLogStore
import com.github.josh910830.portablemq.tests.fixture.messageFixture
import com.ninjasquad.springmockk.SpykBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@EnablePortableMQ
class ConsumptionLoggingDecoratorTest(
    @Autowired val consumptionLoggingDecorator: ConsumptionLoggingDecorator,
    @SpykBean val consumptionLogStore: ConsumptionLogStore
) : DescribeSpec({

    describe("consume") {
        val groupId = "groupId"
        val message = messageFixture()
        val action = mockk<Runnable>(relaxed = true)

        context("not duplicated") {
            it("run save") {
                consumptionLoggingDecorator.consume(groupId, message, action)

                verify { action.run() }
                verify { consumptionLogStore.save(any()) }
            }
        }

        context("duplicated") {
            val consumptionLog = ConsumptionLog(groupId, message)
            consumptionLogStore.save(consumptionLog)

            it("skip action") {
                consumptionLoggingDecorator.consume(groupId, message, action)

                verify(exactly = 0) { action.run() }
            }
        }

    }

})
