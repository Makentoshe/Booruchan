package com.makentoshe.booruchan.feature.context

data class BooruSearchSettings(
    val requestedPostsPerPageCount: Int = 30,
    val initialPageNumber: Int = 0,
    val hint: String = "",
    val tagSeparator: String = " "
) {
    init {
        require(requestedPostsPerPageCount > 0) { "requestedPostsPerPage must be > 0" }
        require(initialPageNumber >= 0) { "requestedPostsPerPage must be >= 0" }
    }
}