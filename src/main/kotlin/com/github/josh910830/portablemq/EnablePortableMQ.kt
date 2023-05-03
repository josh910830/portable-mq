package com.github.josh910830.portablemq

import org.springframework.context.annotation.Import
import kotlin.annotation.AnnotationTarget.CLASS

@Target(CLASS)
@Import(PortableMQConfiguration::class)
annotation class EnablePortableMQ
