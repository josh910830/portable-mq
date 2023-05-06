package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.core.producer.Producer
import com.github.josh910830.portablemq.producer.spring.SpringProducer

@Producer("example-message")
class ExampleSpringProducer : SpringProducer<ExampleMessage>
