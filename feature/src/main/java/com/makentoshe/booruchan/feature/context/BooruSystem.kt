package com.makentoshe.booruchan.feature.context

/** Lust of supported booru systems */
sealed class BooruSystem {
    abstract val name: String

//    object DanbooruSystem: BooruSystem() {
//        override val name: String get() = "danbooru"
//    }
//
//    object DanbooruOldSystem: BooruSystem() {
//        override val name: String get() = "danbooru-old"
//    }

    /** Gelbooru Beta 0.2.0 */
    object Gelbooru020System: BooruSystem() {
        override val name: String get() = "gelbooru-02"
    }

    /** Gelbooru Beta 0.2.5 */
    object Gelbooru025System: BooruSystem() {
        override val name: String get() = "gelbooru-025"
    }
//
//    object Gelbooru01System: BooruSystem() {
//        override val name: String get() = "gelbooru-01"
//    }
//
//    object MoebooruSystem: BooruSystem() {
//        override val name: String get() = "moebooru"
//    }
//
//    object ShimmieSystem: BooruSystem() {
//        override val name: String get() = "Shimmie"
//    }

    data class UndefinedSystem(override val name: String): BooruSystem()

}

