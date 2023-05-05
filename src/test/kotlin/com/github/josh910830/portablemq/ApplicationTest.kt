package com.github.josh910830.portablemq

import com.github.josh910830.portablemq.tests.example.ExampleKafkaProducer
import com.github.josh910830.portablemq.tests.example.ExampleSpringProducer
import io.kotest.core.spec.style.DescribeSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@EnablePortableMQ
class ApplicationTest(
    @Autowired val exampleSpringProducer: ExampleSpringProducer,
    @Autowired val exampleKafkaProducer: ExampleKafkaProducer
) : DescribeSpec({

    it("load context") {
    }

})
