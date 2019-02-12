package com.makentoshe.booruchan.booru.model

/** This object controls the content screen state. */
interface ContentScreenController {
    /** Called when required to show posts screen */
    fun onPostsClicked()
    /** Called when required to show account screen */
    fun onAccountClicked()
}