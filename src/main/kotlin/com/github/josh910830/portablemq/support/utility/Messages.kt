package com.github.josh910830.portablemq.support.utility

import com.github.josh910830.portablemq.kafka.producer.ObjectMapperHolder

class Messages {
    companion object {
        fun flatten(obj: Any): Flat {
            return Flat(
                obj.javaClass.name,
                ObjectMapperHolder.get().writeValueAsString(obj)
            )
        }

        fun animate(type: String, content: String): Any {
            return ObjectMapperHolder.get().readValue(content, Class.forName(type))
        }
    }

    data class Flat(
        val type: String,
        val content: String
    )
}
