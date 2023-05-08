package com.github.josh910830.portablemq.support.autoconfiguration

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory

abstract class DefaultBeanClass {

    protected val log = LoggerFactory.getLogger(javaClass)!!


    @PostConstruct
    fun warnDefault() {
        log.warn("This is DefaultBean for Convenience. Implement ${javaClass.interfaces.first().simpleName} of PortableMQ.")
    }

}
