package com.github.polydome.popstash.parser.readability

import net.dankito.readability4j.Readability4J
import javax.inject.Inject

class ReadabilityFactory @Inject constructor() {
    fun forUri(uri: String, html: String): Readability4J =
            Readability4J(uri, html)
}