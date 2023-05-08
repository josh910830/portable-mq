package com.github.josh910830.portablemq.core.consumer.deadletter.interfaces

interface RedriveTokenManager {

    fun issue(deadletterId: String): String
    fun authenticate(deadletterId: String, redriveToken: String): Boolean

}
