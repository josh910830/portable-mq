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
                ?: throw RuntimeException("@SubscribeSpring(topic:String?) is required to ${method.name}.")

            val defined = annotation.topic
            val default = method.parameterTypes[0].name
            return if (defined == "") default else defined
        }

        fun <T : Message> extractTopic(producerClass: Class<out BrokerProducer<T>>, message: T): String {
            val annotation = producerClass.getAnnotation(Producer::class.java)
                ?: throw RuntimeException("@Producer(topic:String?) is required to ${producerClass.name}.")

            val defined = annotation.topic
            val default = message.javaClass.name
            return if (defined == "") default else defined
        }

        fun extractGroupId(springListener: SpringListener, portableMQProperties: PortableMQProperties): String {
            val defined = springListener.groupId
            val default = portableMQProperties.consumer.groupId
            return if (defined == "") default else defined
        }

    }
}
