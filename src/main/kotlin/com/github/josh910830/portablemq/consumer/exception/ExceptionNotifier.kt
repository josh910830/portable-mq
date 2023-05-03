package com.github.josh910830.portablemq.consumer.exception

interface ExceptionNotifier {

    fun notify(e: Exception)

}
