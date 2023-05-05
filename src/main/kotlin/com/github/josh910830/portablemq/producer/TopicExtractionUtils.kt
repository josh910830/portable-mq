package com.github.josh910830.portablemq.producer

import com.github.josh910830.portablemq.message.Message

class TopicExtractionUtils {
    companion object {

        fun <T : Message> extractTopic(javaClass: Class<out BrokerProducer<T>>, message: T): String {
            val annotation = javaClass.getAnnotation(Producer::class.java)!! // TODO message
            return if (annotation.topic == "default") message.javaClass.name else annotation.topic
        }

    }
}
