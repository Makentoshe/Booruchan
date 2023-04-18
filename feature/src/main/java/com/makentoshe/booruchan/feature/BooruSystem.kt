package com.makentoshe.booruchan.feature

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

    object Gelbooru02System: BooruSystem() {
        override val name: String get() = "gelbooru-02"
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

