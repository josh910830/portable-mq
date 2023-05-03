package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.producer.SpringProducer
import org.springframework.boot.test.context.TestComponent

@TestComponent
class ExampleSpringProducer : SpringProducer<ExampleMessage>
