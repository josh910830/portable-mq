package com.github.josh910830.portablemq.spring.event

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.concurrent.Executor

@Component
class SpringMessageEventDispatcher(
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
