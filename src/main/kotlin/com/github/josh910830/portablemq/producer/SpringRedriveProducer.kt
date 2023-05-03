package com.github.josh910830.portablemq.producer

import com.github.josh910830.portablemq.message.Message
import com.github.josh910830.portablemq.producer.SpringProducer
import org.springframework.stereotype.Component

@Component
class SpringRedriveProducer : SpringProducer<Message>
