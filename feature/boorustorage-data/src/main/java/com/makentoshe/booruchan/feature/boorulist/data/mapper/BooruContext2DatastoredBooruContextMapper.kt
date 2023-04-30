package com.makentoshe.booruchan.feature.boorulist.data.mapper

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.DatastoredBooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.DatastoredBooruSearchSettings
import com.makentoshe.booruchan.feature.boorulist.domain.DatastoredBooruSettings
import javax.inject.Inject

internal class BooruContext2DatastoredBooruContextMapper @Inject constructor() {
    fun map(booruContext: BooruContext) = DatastoredBooruContext(
        title = booruContext.title,
        system = booruContext.system.name,
        host = booruContext.host.url,
        settings = DatastoredBooruSettings(
            searchSettings = DatastoredBooruSearchSettings(
                requestedPostsPerPageCount = booruContext.settings.searchSettings.requestedPostsPerPageCount,
            )
        )
    )
}