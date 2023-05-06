package com.github.josh910830.portablemq.spring.event

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.concurrent.Executor

@Component
class SpringMessageEventDispatcher(
    private val springListenerResolver: SpringListenerResolver,
    private val executor: Executor
) {

    @EventListener
    fun on(e: SpringMessageEvent) {
        springListenerResolver.get(e.topic).forEach {
            executor.execute {
                it.method.invoke(it.bean, e.message)
            }
        }
    }

}
