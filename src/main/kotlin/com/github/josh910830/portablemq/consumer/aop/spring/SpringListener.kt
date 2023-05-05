package com.github.josh910830.portablemq.consumer.aop.spring

import kotlin.annotation.AnnotationTarget.FUNCTION

@Target(FUNCTION)
annotation class SpringListener(

    val groupId: String = "",
    val topic: String = "",

    val useConsumptionLog: Boolean = true,
    val useDeadletter: Boolean = true

)
