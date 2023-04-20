package com.makentoshe.booruchan.feature.serialization.mapper

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.serialization.SerializedBooruContext

class BooruContext2SerializedBooruContextMapper {
    fun map(booruContext: BooruContext) = SerializedBooruContext(
        booruContext.title,
        booruContext.system.name,
        booruContext.host.url,
    )
}