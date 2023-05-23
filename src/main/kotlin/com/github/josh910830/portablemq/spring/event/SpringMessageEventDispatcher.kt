package com.github.josh910830.portablemq.spring.event

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.concurrent.Executor

@Component
class SpringMessageEventDispatcher(
    private val springListenerResolver: SpringListenerResolver,
    private val portableMqExecutor: Executor
) {

    @EventListener
    fun on(e: SpringMessageEvent) {
        // TODO support variant type
        springListenerResolver.get(e.topic).forEach {
            portableMqExecutor.execute {
                it.method.invoke(it.bean, e.message)
            }
        }
    }

}
