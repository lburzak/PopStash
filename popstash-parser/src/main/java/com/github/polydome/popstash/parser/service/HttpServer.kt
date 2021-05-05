package com.github.polydome.popstash.parser.service

interface HttpServer {
    fun fetchHtml(url: String): String
}