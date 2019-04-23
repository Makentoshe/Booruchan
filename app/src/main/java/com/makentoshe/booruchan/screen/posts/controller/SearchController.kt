package com.makentoshe.booruchan.screen.posts.controller

import com.makentoshe.booruchan.api.component.tag.Tag

/**
 * Interface for controlling the search process.
 */
interface SearchController {

    /**
     * Starts a new search with the current set of the tags
     */
    fun startSearch(tags: Set<Tag>)

    /**
     * Calls when the new search was started
     */
    fun onSearchStarted(action: (Set<Tag>) -> Unit)

}