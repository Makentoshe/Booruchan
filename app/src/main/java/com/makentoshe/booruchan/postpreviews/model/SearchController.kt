package com.makentoshe.booruchan.postpreviews.model

import com.makentoshe.booruapi.Tag

/**
 * Controller interface for performing search actions.
 */
interface SearchController {
    /**
     * Starts a new search with [tags].
     */
    fun startSearch(tags: Set<Tag>)

    /**
     * Performs an [action] on new search started event.
     */
    fun onSearchStartedListener(action: (Set<Tag>) -> Unit)
}