package com.github.josh910830.portablemq.core.consumer.deadletter

data class DeadletterProcessResult(
    val deadletterId: String,
    val success: Boolean,
    val error: String? = null
) {
    companion object {
        fun success(deadletterId: String): DeadletterProcessResult {
            return DeadletterProcessResult(deadletterId, true)
        }

        fun fail(deadletterId: String, e: Exception): DeadletterProcessResult {
            return DeadletterProcessResult(deadletterId, false, "${e.javaClass.simpleName}: ${e.message}")
        }
    }
}
