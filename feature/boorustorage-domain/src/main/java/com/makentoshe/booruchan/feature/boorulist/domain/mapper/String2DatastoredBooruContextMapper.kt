package com.makentoshe.booruchan.feature.boorulist.domain.mapper

import com.makentoshe.booruchan.feature.boorulist.domain.DatastoredBooruContext

interface String2DatastoredBooruContextMapper {
    fun map(string: String): DatastoredBooruContext
}