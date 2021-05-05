package com.github.polydome.popstash.network

import com.github.polydome.popstash.parser.service.HttpServer
import java.net.URL

class JavaHttpServer : HttpServer {
    override fun fetchHtml(url: String): String {
        val connection = URL(url).openConnection()
        val inputStream = connection.getInputStream()

        inputStream.reader().useLines {
            return it.joinToString()
        }
    }
}