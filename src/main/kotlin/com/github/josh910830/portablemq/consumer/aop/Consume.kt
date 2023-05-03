package com.github.josh910830.portablemq.consumer.aop

import com.github.josh910830.portablemq.consumer.aop.BrokerType.DEFAULT
import kotlin.annotation.AnnotationTarget.FUNCTION

@Target(FUNCTION)
annotation class Consume(

    val useConsumptionLog: Boolean = true,
    val consumerGroup: String = "default",

    val useDeadletter: Boolean = true,
    val brokerType: BrokerType = DEFAULT

)
