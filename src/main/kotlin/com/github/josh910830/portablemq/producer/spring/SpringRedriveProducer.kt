package com.github.josh910830.portablemq.producer.spring

import com.github.josh910830.portablemq.message.Message
import com.github.josh910830.portablemq.producer.Producer

@Producer
class SpringRedriveProducer : SpringProducer<Message>
