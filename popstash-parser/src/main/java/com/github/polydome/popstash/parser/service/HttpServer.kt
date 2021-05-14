package com.github.polydome.popstash.parser.service

interface HttpServer {
    fun fetchHtml(url: String): Result

    sealed class Result {
        data class Failure(val error: Error): Result()
        data class Success(val html: String): Result()
    }

    enum class Error {
        NO_SUCH_RESOURCE
    }
}