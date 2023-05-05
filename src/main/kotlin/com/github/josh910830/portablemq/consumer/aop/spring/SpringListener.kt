package com.github.josh910830.portablemq.consumer.aop.spring

import kotlin.annotation.AnnotationTarget.FUNCTION

@Target(FUNCTION)
annotation class SpringListener(

    val topic: String = "default",

    val useConsumptionLog: Boolean = true,
    val consumerGroup: String = "default",

    val useDeadletter: Boolean = true

)
