package com.github.josh910830.portablemq.support.template

import com.github.josh910830.portablemq.core.consumer.Broker
import com.github.josh910830.portablemq.core.consumer.deadletter.Deadletter
import com.github.josh910830.portablemq.core.consumer.deadletter.interfaces.DeadletterStore
import com.github.josh910830.portablemq.core.message.Message
import com.github.josh910830.portablemq.kafka.producer.ObjectMapperHolder

abstract class DeadletterJsonStore : DeadletterStore {

    private fun jsonOf(deadletter: Deadletter): String {
        val om = ObjectMapperHolder.get()
        with(deadletter) {
            val data = mapOf<String, String>(
                "id" to id,
                "topic" to topic,
                "messageClass" to message.javaClass.canonicalName,
                "jsonMessage" to om.writeValueAsString(message),
                "broker" to broker.name,
                "redriven" to redriven.toString()
            )
            return om.writeValueAsString(data)
        }
    }

    private fun deadletterOf(json: String): Deadletter {
        val om = ObjectMapperHolder.get()
        val data = om.readValue(json, Map::class.java)
        with(data) {
            return Deadletter(
                get("id") as String,
                get("topic") as String,
                om.readValue(get("jsonMessage") as String, Class.forName(get("messageClass") as String)) as Message,
                Broker.valueOf(get("broker") as String),
                (get("redriven") as String) == "true"
            )
        }
    }


    final override fun save(deadletter: Deadletter) {
        doSave(deadletter.id, jsonOf(deadletter))
    }

    abstract fun doSave(deadletterId: String, json: String)


    final override fun findAll(): List<Deadletter> {
        return doFindAll().map { deadletterOf(it) }
    }

    abstract fun doFindAll(): List<String>


    final override fun findById(deadletterId: String): Deadletter {
        return deadletterOf(doFindById(deadletterId))
    }

    abstract fun doFindById(deadletterId: String): String

}
