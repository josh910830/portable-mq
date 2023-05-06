package com.github.josh910830.portablemq.consumer.aop.kafka

import com.github.josh910830.portablemq.core.exception.PortableMQException
import com.github.josh910830.portablemq.core.consumer.Consumer
import jakarta.annotation.PostConstruct
import org.springframework.context.ApplicationContext
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Method

@Component
class KafkaConsumerResolver(
    private val applicationContext: ApplicationContext
) {

    private lateinit var parseMethodMap: Map<Any, Method>
    private lateinit var handleMethodMap: Map<Any, Method>

    @PostConstruct
    fun initialize() {
        val kafkaConsumers = applicationContext.getBeansWithAnnotation(Consumer::class.java).values.filter { bean ->
            val kafkaListeners = ReflectionUtils.getAllDeclaredMethods(bean.javaClass).mapNotNull { method ->
                method.getAnnotation(KafkaListener::class.java)
            }
            val isKafkaConsumer = kafkaListeners.isNotEmpty()
            if (isKafkaConsumer) {
                if (kafkaListeners.size > 1) throw PortableMQException("@Consumer should have only one @KafkaListener.")
                if (kafkaListeners.first().topics.size != 1) throw PortableMQException("@KafkaListener.topics should have 1 element.")
            }
            isKafkaConsumer
        }

        val tempParseMethodMap: MutableMap<Any, Method> = mutableMapOf()
        val tempHandleMethodMap: MutableMap<Any, Method> = mutableMapOf()
        kafkaConsumers.forEach { bean ->
            ReflectionUtils.getAllDeclaredMethods(bean.javaClass).forEach { method ->
                method.getAnnotation(Parse::class.java)?.let { tempParseMethodMap[bean] = method }
                method.getAnnotation(Handle::class.java)?.let { tempHandleMethodMap[bean] = method }
            }
            tempHandleMethodMap[bean] ?: throw PortableMQException("@Handle fun handle(message:Message) is required.")
        }
        parseMethodMap = tempParseMethodMap
        handleMethodMap = tempHandleMethodMap
    }


    fun getParseMethod(bean: Any): Method? {
        return parseMethodMap[bean]
    }

    fun getHandleMethod(bean: Any): Method {
        return handleMethodMap[bean]!!
    }

}
