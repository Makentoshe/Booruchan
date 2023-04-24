package com.makentoshe.booruchan.library.logging

sealed interface LogFingerprint {

    val title: String

    object Network : LogFingerprint {
        override val title = "booru_network_"
    }

    object Screen : LogFingerprint {
        override val title = "booru_screen_"
    }

    object ViewModel : LogFingerprint {
        override val title = "booru_viewmodel_"
    }

    object Internal: LogFingerprint {
        override val title = "booru_internal_"
    }

}
