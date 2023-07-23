package com.makentoshe.booruchan.library.logging

sealed interface LogFingerprint {

    val title: String

    object Network : LogFingerprint {
        override val title = "booru_network_"
    }

    object Screen : LogFingerprint {
        override val title = "booru_screen_"
    }

    object Internal: LogFingerprint {
        override val title = "booru_internal_"
    }

}
