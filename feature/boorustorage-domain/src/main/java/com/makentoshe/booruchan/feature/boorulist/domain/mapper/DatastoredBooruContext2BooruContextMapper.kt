package com.makentoshe.booruchan.feature.boorulist.domain.mapper

import com.makentoshe.booruchan.feature.context.BooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.DatastoredBooruContext

interface DatastoredBooruContext2BooruContextMapper {

    fun map(storedBooruContext: DatastoredBooruContext): BooruContext
}
