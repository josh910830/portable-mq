package com.github.josh910830.portablemq.consumer.dispatcher.spring

import java.lang.reflect.Method

data class Resolution(
    val method: Method,
    val bean: Any
)
