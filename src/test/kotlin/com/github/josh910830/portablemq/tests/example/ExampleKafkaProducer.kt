package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.core.producer.Producer
import com.github.josh910830.portablemq.kafka.producer.KafkaProducer

@Producer("example-message")
class ExampleKafkaProducer : KafkaProducer<ExampleMessage>
