package com.makentoshe.booruchan.screen.boorucontent.viewmodel

/** Destinations that can be triggered */
sealed interface BoorucontentDestination {

    /** Takes user on the previous screen */
    object BackDestination: BoorucontentDestination
}