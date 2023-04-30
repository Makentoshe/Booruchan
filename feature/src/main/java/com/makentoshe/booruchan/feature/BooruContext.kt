package com.makentoshe.booruchan.feature

data class BooruContext(
    /** Booru title (Gelbooru, Danbooru, etc)*/
    val title: String,
    /** The system booru uses */
    val system: BooruSystem,
    /** Detail info about host */
    val host: BooruHost,
    /** Info about current booru settings */
    val settings: BooruSettings,
)
