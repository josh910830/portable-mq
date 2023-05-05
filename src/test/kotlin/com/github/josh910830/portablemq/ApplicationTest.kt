package com.github.josh910830.portablemq

import io.kotest.core.spec.style.DescribeSpec
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@EnablePortableMQ
class ApplicationTest(
) : DescribeSpec({

    it("load context") {
    }

})
