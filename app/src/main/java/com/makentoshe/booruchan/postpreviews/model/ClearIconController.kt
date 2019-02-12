package com.makentoshe.booruchan.postpreviews.model

/**
 * Interface for the object that will wrapping [ClearIconRxController].
 */
interface ClearIconController {
    /** Call click event. */
    fun clearIconClick()

    /** Handle click event*/
    fun onClearIconClickListener(action: () -> Unit)
}