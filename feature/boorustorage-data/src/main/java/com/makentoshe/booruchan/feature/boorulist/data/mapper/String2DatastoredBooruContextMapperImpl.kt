package com.makentoshe.booruchan.feature.boorulist.data.mapper

import com.makentoshe.booruchan.feature.boorulist.domain.DatastoredBooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.mapper.String2DatastoredBooruContextMapper
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class String2DatastoredBooruContextMapperImpl @Inject constructor(

) : String2DatastoredBooruContextMapper {
    override fun map(string: String): DatastoredBooruContext {
        return Json.decodeFromString(string)
    }
}