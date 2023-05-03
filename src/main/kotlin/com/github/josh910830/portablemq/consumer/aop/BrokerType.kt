package com.github.josh910830.portablemq.consumer.aop

import com.github.josh910830.portablemq.consumer.deadletter.Broker

enum class BrokerType {
    SPRING {
        override fun toBroker(): Broker {
            return Broker.SPRING
        }
    },
    KAFKA {
        override fun toBroker(): Broker {
            return Broker.KAFKA
        }
    },
    DEFAULT {
        override fun toBroker(): Broker {
            throw IllegalStateException("BrokerType.DEFAULT cannot be Broker")
        }
    }
    ;

    abstract fun toBroker(): Broker

}
