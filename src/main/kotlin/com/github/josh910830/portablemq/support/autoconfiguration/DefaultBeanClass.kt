package com.github.josh910830.portablemq.support.autoconfiguration

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory

abstract class DefaultBeanClass {

    @PostConstruct
    fun warn() {
        LoggerFactory.getLogger(javaClass)
            .warn("This is DefaultBean for Convenience. Implement ${javaClass.interfaces.first().simpleName} of PortableMQ.")
    }

}
