package com.github.josh910830.portablemq.consumer.dispatcher.spring

import com.github.josh910830.portablemq.consumer.Consumer
import com.github.josh910830.portablemq.consumer.aop.spring.SpringListener
import com.github.josh910830.portablemq.utility.Extracts.Companion.extractTopic
import jakarta.annotation.PostConstruct
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils

@Component
class SpringConsumerResolver(
    private val applicationContext: ApplicationContext
) {

    private lateinit var map: Map<String, List<Resolution>>

    @PostConstruct
    fun initialize() {
        val temp: MutableMap<String, MutableList<Resolution>> = mutableMapOf()
        applicationContext.getBeansWithAnnotation(Consumer::class.java).values.forEach { bean ->
            ReflectionUtils.getAllDeclaredMethods(bean.javaClass).filter { method ->
                method.isAnnotationPresent(SpringListener::class.java)
            }.forEach { springListenerMethod ->
                val topic = extractTopic(springListenerMethod)

                val list = temp.getOrDefault(topic, mutableListOf())
                list.add(Resolution(springListenerMethod, bean))
                temp[topic] = list
            }
        }
        map = temp
    }


    fun get(topic: String): List<Resolution> {
        return map[topic] ?: emptyList()
    }

}
