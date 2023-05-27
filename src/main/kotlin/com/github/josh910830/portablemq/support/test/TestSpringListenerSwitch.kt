package com.github.josh910830.portablemq.support.test

import com.github.josh910830.portablemq.spring.event.SpringListenerSwitch
import org.slf4j.LoggerFactory

class TestSpringListenerSwitch : SpringListenerSwitch {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun isActive(): Boolean {
        log.debug("Skip SpringMessageEvent.")
        return false
    }

}