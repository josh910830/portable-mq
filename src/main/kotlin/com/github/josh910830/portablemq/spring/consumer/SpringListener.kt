package com.github.josh910830.portablemq.spring.consumer

import kotlin.annotation.AnnotationTarget.FUNCTION

@Target(FUNCTION)
annotation class SpringListener(

    val topic: String,
    val groupId: String = ""

)
