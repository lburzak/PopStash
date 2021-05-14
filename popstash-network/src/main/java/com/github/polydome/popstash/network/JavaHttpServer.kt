package com.github.polydome.popstash.network

import com.github.polydome.popstash.parser.service.HttpServer
import com.github.polydome.popstash.parser.service.HttpServer.Error
import com.github.polydome.popstash.parser.service.HttpServer.Result
import java.io.FileNotFoundException
import java.net.URL
import javax.inject.Inject

class JavaHttpServer @Inject constructor() : HttpServer {
    override fun fetchHtml(url: String): Result {
        val connection = URL(url).openConnection()

        val inputStream = try {
            connection.getInputStream()
        } catch (e: FileNotFoundException) {
            return Result.Failure(Error.NO_SUCH_RESOURCE)
        }

        val html = inputStream.reader().useLines {
            it.joinToString()
        }

        return Result.Success(html)
    }
}