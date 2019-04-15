package com.makentoshe.booruchan.api.component.parser

import java.io.InputStream
import java.util.*

interface Parser<R> {

    fun parse(inputStream: InputStream) = parse(Scanner(inputStream).useDelimiter("\\A").next())

    fun parse(data: String): R
}