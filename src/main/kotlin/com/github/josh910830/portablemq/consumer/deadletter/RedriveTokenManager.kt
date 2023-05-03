package com.github.josh910830.portablemq.consumer.deadletter

interface RedriveTokenManager {

    fun issue(deadletterId: String): String
    fun authenticate(deadletterId: String, redriveToken: String): Boolean

}
