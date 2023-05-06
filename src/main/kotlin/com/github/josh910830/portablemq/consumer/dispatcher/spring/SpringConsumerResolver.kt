package com.github.josh910830.portablemq.consumer.dispatcher.spring

import com.github.josh910830.portablemq.core.consumer.Consumer
import com.github.josh910830.portablemq.consumer.aop.spring.SpringListener
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
            ReflectionUtils.getAllDeclaredMethods(bean.javaClass).forEach { method ->
                method.getAnnotation(SpringListener::class.java)?.let { a ->
                    val list = temp[a.topic] ?: mutableListOf()
                    list.add(Resolution(method, bean))
                    temp[a.topic] = list
                }
            }
        }
        map = temp
    }


    fun get(topic: String): List<Resolution> {
        return map[topic] ?: emptyList()
    }

}
