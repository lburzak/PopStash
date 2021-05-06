package com.github.polydome.popstash.network

import com.github.polydome.popstash.parser.service.HttpServer
import java.net.URL
import javax.inject.Inject

class JavaHttpServer @Inject constructor() : HttpServer {
    override fun fetchHtml(url: String): String {
        val connection = URL(url).openConnection()
        val inputStream = connection.getInputStream()

        inputStream.reader().useLines {
            return it.joinToString()
        }
    }
}