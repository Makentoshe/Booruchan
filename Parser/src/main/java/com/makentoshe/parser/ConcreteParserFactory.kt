package com.makentoshe.parser

interface ConcreteParserFactory<T> {
    fun buildParser(parserStyle: ParserStyle): Parser<T>
}