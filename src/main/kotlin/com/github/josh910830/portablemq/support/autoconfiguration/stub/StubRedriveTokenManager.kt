package com.github.josh910830.portablemq.support.autoconfiguration.stub

import com.github.josh910830.portablemq.core.consumer.deadletter.RedriveTokenManager
import com.github.josh910830.portablemq.support.autoconfiguration.DefaultBeanClass

class StubRedriveTokenManager : DefaultBeanClass(), RedriveTokenManager {

    private val token = "token"

    override fun issue(deadletterId: String): String {
        return token
    }

    override fun authenticate(deadletterId: String, redriveToken: String): Boolean {
        return redriveToken == token
    }

}