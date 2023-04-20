package com.makentoshe.booruchan.feature.serialization

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.serialization.mapper.BooruContextSerializable2BooruContextMapper
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class BooruContextDeserialize(
    private val mapper: BooruContextSerializable2BooruContextMapper,
) {
    fun deserialize(string: String): BooruContext {
        return mapper.map(Json.decodeFromString(string))
    }
}

