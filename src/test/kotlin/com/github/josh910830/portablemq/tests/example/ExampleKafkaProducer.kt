package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.producer.Producer
import com.github.josh910830.portablemq.producer.kafka.KafkaProducer

@Producer
class ExampleKafkaProducer : KafkaProducer<ExampleMessage>
