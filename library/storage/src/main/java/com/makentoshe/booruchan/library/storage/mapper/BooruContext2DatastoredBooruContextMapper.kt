package com.makentoshe.booruchan.library.storage.mapper

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.library.storage.domain.DatastoredBooruContext
import javax.inject.Inject

class BooruContext2DatastoredBooruContextMapper @Inject constructor() {
    fun map(booruContext: BooruContext) = DatastoredBooruContext(
        booruContext.title,
        booruContext.system.name,
        booruContext.host.url,
    )
}