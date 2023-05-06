package com.github.josh910830.portablemq.consumer.log

import com.github.josh910830.portablemq.core.consumer.log.ConsumptionLog
import com.github.josh910830.portablemq.core.consumer.log.ConsumptionLogDecorator
import com.github.josh910830.portablemq.tests.example.ExampleConsumptionLogStore
import com.github.josh910830.portablemq.tests.fixture.messageFixture
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify

class ConsumptionLogDecoratorTest : DescribeSpec({

    val consumptionLogStore = spyk(ExampleConsumptionLogStore())
    val consumptionLogDecorator = ConsumptionLogDecorator(consumptionLogStore)

    describe("consume") {
        val groupId = "groupId"
        val message = messageFixture()
        val action = mockk<Runnable>(relaxed = true)

        context("not duplicated") {
            it("run save") {
                consumptionLogDecorator.consume(groupId, message, action)

                verify { action.run() }
                verify { consumptionLogStore.save(any()) }
            }
        }

        context("duplicated") {
            val consumptionLog = ConsumptionLog(groupId, message)
            consumptionLogStore.save(consumptionLog)

            it("skip action") {
                consumptionLogDecorator.consume(groupId, message, action)

                verify(exactly = 0) { action.run() }
            }
        }

    }

})