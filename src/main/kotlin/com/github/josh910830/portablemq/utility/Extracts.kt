package com.github.josh910830.portablemq.utility

import com.github.josh910830.portablemq.PortableMQProperties
import com.github.josh910830.portablemq.consumer.aop.spring.SpringListener
import com.github.josh910830.portablemq.message.Message
import com.github.josh910830.portablemq.producer.BrokerProducer
import com.github.josh910830.portablemq.producer.Producer
import java.lang.reflect.Method

class Extracts {
    companion object {

        fun extractTopic(method: Method): String {
            val annotation = method.getAnnotation(SpringListener::class.java)
                ?: throw RuntimeException("@SpringListener(topic:String?) is required to ${method.name}.")

            val defined = annotation.topic
            val default = method.parameterTypes[0].name
            return if (defined == "default") default else defined
        }

        fun <T : Message> extractTopic(producerClass: Class<out BrokerProducer<T>>, message: T): String {
            val annotation = producerClass.getAnnotation(Producer::class.java)
                ?: throw RuntimeException("@Producer(topic:String?) is required to ${producerClass.name}.")

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
