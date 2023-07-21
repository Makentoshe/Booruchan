package com.makentoshe.booruchan.extension

import com.makentoshe.booruchan.feature.context.BooruSystem

data class BooruContext(
    val title: String,
    val system: BooruSystem = BooruSystem.UndefinedSystem("empty"),
    val host: String,
)
