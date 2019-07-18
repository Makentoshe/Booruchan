package com.makentoshe.boorusamplesview

import java.io.Serializable

/**
 * Navigator component for an image screen.
 * ALL PARTS OF A [BooruSampleScreenNavigator] SHOULD BE [java.io.Serializable], BECAUSE THIS COMPONENT STORES IN A
 * [android.os.Bundle] COMPONENT (for example a [androidx.fragment.app.Fragment.getArguments]).
 */
interface BooruSampleScreenNavigator : Serializable {

    /** Performs a current screen exit and returns to a previous screen */
    fun close()
}