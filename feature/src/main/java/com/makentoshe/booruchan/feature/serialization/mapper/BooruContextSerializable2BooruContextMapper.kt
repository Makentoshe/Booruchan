package com.makentoshe.booruchan.feature.serialization.mapper

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.BooruHost
import com.makentoshe.booruchan.feature.BooruSystem
import com.makentoshe.booruchan.feature.serialization.SerializedBooruContext

class BooruContextSerializable2BooruContextMapper {
    fun map(serializedBooruContext: SerializedBooruContext) = BooruContext(
        title = serializedBooruContext.title,
        system = defineBooruSystem(serializedBooruContext.system),
        host = BooruHost(serializedBooruContext.host),
    )

    private fun defineBooruSystem(string: String) = when(string) {
        BooruSystem.Gelbooru02System.name -> BooruSystem.Gelbooru02System
        else -> BooruSystem.UndefinedSystem(string)
    }
}