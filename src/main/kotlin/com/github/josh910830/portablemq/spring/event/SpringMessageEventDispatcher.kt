package com.github.josh910830.portablemq.spring.event

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.concurrent.Executor

@Component
class SpringMessageEventDispatcher(
    private val springListenerResolver: SpringListenerResolver,
    private val portableMqExecutor: Executor,
    private val springListenerSwitch: SpringListenerSwitch
) {

    @EventListener
    fun on(e: SpringMessageEvent) {
        if (!springListenerSwitch.isActive()) return

        // TODO support variant type
        springListenerResolver.get(e.topic).forEach {
            portableMqExecutor.execute {
                it.method.invoke(it.bean, e.message)
            }
        }
    }

}
