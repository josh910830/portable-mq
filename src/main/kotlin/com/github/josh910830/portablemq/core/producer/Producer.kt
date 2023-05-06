package com.github.josh910830.portablemq.core.producer

import org.springframework.stereotype.Component
import kotlin.annotation.AnnotationTarget.CLASS

@Target(CLASS)
@Component
annotation class Producer(
    val topic: String
)
