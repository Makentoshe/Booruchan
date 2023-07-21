package com.makentoshe.booruchan.extension.parser

import com.makentoshe.booruchan.extension.entity.NetworkPost

interface PostSearchParser {
    fun parse(string: String): List<NetworkPost>
}

