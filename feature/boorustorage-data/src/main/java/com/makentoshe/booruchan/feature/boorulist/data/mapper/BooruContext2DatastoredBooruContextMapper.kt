package com.makentoshe.booruchan.feature.boorulist.data.mapper

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.DatastoredBooruContext
import javax.inject.Inject

internal class BooruContext2DatastoredBooruContextMapper @Inject constructor() {
    fun map(booruContext: BooruContext) = DatastoredBooruContext(
        booruContext.title,
        booruContext.system.name,
        booruContext.host.url,
    )
}