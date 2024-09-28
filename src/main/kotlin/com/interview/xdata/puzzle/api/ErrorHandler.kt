package com.interview.xdata.puzzle.api

import com.interview.xdata.puzzle.api.io.OutputWriter
import com.interview.xdata.puzzle.core.ValidationException
import mu.KLogging

class ErrorHandler(private val outputWriter: OutputWriter) {

    companion object : KLogging()

    fun handleError(throwable: Throwable) {
        when (throwable) {
            is ValidationException -> handleClientError(throwable)
            else -> handleAppError(throwable)
        }
    }

    private fun handleAppError(throwable: Throwable) {
        logger.error(throwable) { "Unexpected error" }
        val message = throwable.message ?: "Unexpected error"
        outputWriter.write(ErrorModel(message))
    }

    private fun handleClientError(err: ValidationException) {
        outputWriter.write(ErrorModel(err.message))
    }
}