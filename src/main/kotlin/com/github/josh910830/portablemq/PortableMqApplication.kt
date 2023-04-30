package com.github.josh910830.portablemq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PortableMqApplication

fun main(args: Array<String>) {
    runApplication<PortableMqApplication>(*args)
}
