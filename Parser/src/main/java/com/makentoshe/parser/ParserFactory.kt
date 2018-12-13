package com.makentoshe.parser

class ParserFactory {
    fun buildFactory(type: ParserType) = when (type) {
        ParserType.TAG -> TagParserFactory()
    }
}

