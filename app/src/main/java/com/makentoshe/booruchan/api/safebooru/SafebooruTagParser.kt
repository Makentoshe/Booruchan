package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.Tag

class SafebooruTagParser : Parser<List<Tag>> {
    override fun parse(data: String): List<Tag> {
        return emptyList()
    }
}