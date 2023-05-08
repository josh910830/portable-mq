package com.github.josh910830.portablemq.kafka.consumer.badletter.api

data class BadletterProcessResult(
    val badletterId: String,
    val success: Boolean,
    val error: String? = null
) {
    companion object {
        fun success(deadletterId: String): BadletterProcessResult {
            return BadletterProcessResult(deadletterId, true)
        }

        fun fail(deadletterId: String, e: Exception): BadletterProcessResult {
            return BadletterProcessResult(deadletterId, false, "${e.javaClass.simpleName}: ${e.message}")
        }
    }
}
