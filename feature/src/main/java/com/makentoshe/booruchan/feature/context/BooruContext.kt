package com.makentoshe.booruchan.feature.context

data class BooruContext(
    /** Booru title (Gelbooru, Danbooru, etc)*/
    val title: String,
    /** The system booru uses */
    val system: BooruSystem,
    /** Detail info about host */
    val host: BooruHost,
    /** Info about current booru settings */
    val settings: BooruSettings,
) {
    companion object {
        val EmptyContext = BooruContext(
            title = "Empty",
            system = BooruSystem.UndefinedSystem("Empty"),
            host = BooruHost("Empty"),
            settings = BooruSettings(
                searchSettings = BooruSearchSettings(),
            )
        )
    }
}