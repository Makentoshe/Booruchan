package com.makentoshe.parser

import com.makentoshe.booruapi.BooruType
import com.makentoshe.booruapi.Tag
import kotlin.reflect.KClass

class ParserFactory {
    fun buildFactory(type: KClass<out BooruType>) = when (type) {
        Tag::class -> TagParserFactory()
        else -> throw UnsupportedOperationException("Parse for type ${type.simpleName} was not defined in ${this.javaClass.simpleName}")
    }
}

