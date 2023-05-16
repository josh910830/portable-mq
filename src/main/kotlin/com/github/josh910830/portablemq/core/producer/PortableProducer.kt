package com.github.josh910830.portablemq.core.producer

import com.github.josh910830.portablemq.core.message.Message

interface PortableProducer<T : Message> {

    fun produce(message: T)

}
