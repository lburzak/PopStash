package com.github.polydome.popstash.network

import com.github.polydome.popstash.parser.service.HttpServer
import com.github.polydome.popstash.parser.service.HttpServer.Result
import java.net.URL
import javax.inject.Inject

class JavaHttpServer @Inject constructor() : HttpServer {
    override fun fetchHtml(url: String): Result {
        val connection = URL(url).openConnection()
        val inputStream = connection.getInputStream()

        val html = inputStream.reader().useLines {
            it.joinToString()
        }

        return Result.Success(html)
    }
}