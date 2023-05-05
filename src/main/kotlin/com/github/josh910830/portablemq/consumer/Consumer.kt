package com.github.josh910830.portablemq.consumer

import org.springframework.stereotype.Component
import kotlin.annotation.AnnotationTarget.CLASS

@Target(CLASS)
@Component
annotation class Consumer
