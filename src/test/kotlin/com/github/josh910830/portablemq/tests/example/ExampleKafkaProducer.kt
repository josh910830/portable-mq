package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.producer.kafka.KafkaProducer
import org.springframework.boot.test.context.TestComponent

@TestComponent
class ExampleKafkaProducer : KafkaProducer<ExampleMessage>
