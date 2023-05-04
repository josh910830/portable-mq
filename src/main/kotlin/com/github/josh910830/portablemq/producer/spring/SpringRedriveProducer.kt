package com.github.josh910830.portablemq.producer.spring

import com.github.josh910830.portablemq.message.Message
import org.springframework.stereotype.Component

@Component
class SpringRedriveProducer : SpringProducer<Message>
