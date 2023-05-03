package com.github.josh910830.portablemq.tests.example

import com.github.josh910830.portablemq.consumer.deadletter.RedriveTokenManager

class ExampleRedriveTokenManager : RedriveTokenManager {

    private val tokenProperty = "token"


    override fun issue(deadletterId: String): String {
        return tokenProperty
    }

    override fun authenticate(deadletterId: String, redriveToken: String): Boolean {
        return redriveToken == tokenProperty
    }

}
