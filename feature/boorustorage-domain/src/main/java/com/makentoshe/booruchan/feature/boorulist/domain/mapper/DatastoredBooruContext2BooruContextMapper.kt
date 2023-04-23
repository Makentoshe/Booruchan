package com.makentoshe.booruchan.feature.boorulist.domain.mapper

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.DatastoredBooruContext

interface DatastoredBooruContext2BooruContextMapper {

    fun map(storedBooruContext: DatastoredBooruContext): BooruContext
}
