package com.makentoshe.booruchan.feature.serialization

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.serialization.mapper.BooruContext2SerializedBooruContextMapper
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class BooruContextSerialize(
    private val mapper: BooruContext2SerializedBooruContextMapper,
) {
    fun serialize(booruContext: BooruContext): String {
        return Json.encodeToString(mapper.map(booruContext))
    }
}
