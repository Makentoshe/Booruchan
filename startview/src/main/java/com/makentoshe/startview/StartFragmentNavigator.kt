package com.makentoshe.startview

import com.makentoshe.boorulibrary.booru.entity.Booru

/**
 * Interface for performing navigation inside start screen
 */
interface StartFragmentNavigator {

    /** Navigate to settings screen */
    fun navigateToSettingsScreen()

    /** Navigate to booru screen */
    fun navigateToBooruScreen(booru: Booru)

}