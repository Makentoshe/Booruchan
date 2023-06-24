package com.makentoshe.booruchan.feature.boorulist.data.mapper

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.BooruHost
import com.makentoshe.booruchan.feature.BooruSearchSettings
import com.makentoshe.booruchan.feature.BooruSettings
import com.makentoshe.booruchan.feature.BooruSystem
import com.makentoshe.booruchan.feature.boorulist.domain.DatastoredBooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.mapper.DatastoredBooruContext2BooruContextMapper
import javax.inject.Inject

class DatastoredBooruContext2BooruContextMapperImpl @Inject constructor(

) : DatastoredBooruContext2BooruContextMapper {

    override fun map(storedBooruContext: DatastoredBooruContext) = BooruContext(
        title = storedBooruContext.title,
        system = defineBooruSystem(storedBooruContext.system),
        host = BooruHost(storedBooruContext.host),
        settings = BooruSettings(
            searchSettings = BooruSearchSettings(
                requestedPostsPerPageCount = storedBooruContext.settings.searchSettings.requestedPostsPerPageCount,
                initialPageNumber = storedBooruContext.settings.searchSettings.initialPageNumber,
                hint = storedBooruContext.settings.searchSettings.hint,
            )
        )
    )

    private fun defineBooruSystem(string: String) = when (string) {
        BooruSystem.Gelbooru02System.name -> BooruSystem.Gelbooru02System
        else -> BooruSystem.UndefinedSystem(string)
    }
}
