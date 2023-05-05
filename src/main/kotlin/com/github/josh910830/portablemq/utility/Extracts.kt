package com.github.josh910830.portablemq.utility

import com.github.josh910830.portablemq.PortableMQProperties
import com.github.josh910830.portablemq.consumer.aop.spring.SpringListener
import com.github.josh910830.portablemq.message.Message
import com.github.josh910830.portablemq.producer.BrokerProducer
import com.github.josh910830.portablemq.producer.Producer

class Extracts {
    companion object {

        fun <T : Message> extractTopic(javaClass: Class<out BrokerProducer<T>>, message: T): String {
            val annotation = javaClass.getAnnotation(Producer::class.java)
                ?: throw RuntimeException("@Producer(topic:String?) required.")

            val defined = annotation.topic
            val default = message.javaClass.name
            return if (defined == "default") default else defined
        }

        fun extractConsumerGroup(springListener: SpringListener, portableMQProperties: PortableMQProperties): String {
            val defined = springListener.consumerGroup
            val default = portableMQProperties.default.consumerGroup
            return if (defined == "default") default else defined
        }

    }
}
