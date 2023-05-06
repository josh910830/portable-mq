package com.github.josh910830.portablemq.consumer.aop

import kotlin.annotation.AnnotationTarget.FUNCTION

@Target(FUNCTION)
annotation class Consume(

    val useConsumptionLog: Boolean = true,
    val useDeadletter: Boolean = true,
    val useBadletter: Boolean = false

)
