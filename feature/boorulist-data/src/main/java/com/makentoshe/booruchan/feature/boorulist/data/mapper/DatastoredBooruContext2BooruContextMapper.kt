package com.makentoshe.booruchan.feature.boorulist.data.mapper

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.BooruHost
import com.makentoshe.booruchan.feature.BooruSystem
import com.makentoshe.booruchan.feature.boorulist.domain.DatastoredBooruContext
import javax.inject.Inject

internal class DatastoredBooruContext2BooruContextMapper @Inject constructor() {

    fun map(storedBooruContext: DatastoredBooruContext) = BooruContext(
        title = storedBooruContext.title,
        system = defineBooruSystem(storedBooruContext.system),
        host = BooruHost(storedBooruContext.host),
    )

    private fun defineBooruSystem(string: String) = when(string) {
        BooruSystem.Gelbooru02System.name -> BooruSystem.Gelbooru02System
        else -> BooruSystem.UndefinedSystem(string)
    }
}
