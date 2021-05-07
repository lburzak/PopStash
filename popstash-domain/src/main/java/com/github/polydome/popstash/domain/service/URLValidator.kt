package com.github.polydome.popstash.domain.service

interface URLValidator {
    fun validateUrl(url: String): Boolean
}