package com.github.josh910830.portablemq.consumer.dispatcher.spring

import com.github.josh910830.portablemq.message.spring.SpringMessageEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.concurrent.Executor

@Component
class SpringMessageDispatcher(
    private val springConsumerResolver: SpringConsumerResolver,
    private val executor: Executor
) {

    @EventListener
    fun on(e: SpringMessageEvent) {
        springConsumerResolver.get(e.topic).forEach {
            executor.execute {
                it.method.invoke(it.bean, e.message)
            }
        }
    }

}
