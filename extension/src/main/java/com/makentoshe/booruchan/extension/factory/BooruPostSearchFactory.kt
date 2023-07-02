package com.makentoshe.booruchan.extension.factory

interface BooruPostSearchFactory {

    /** Initial page number for api. Mostly it is 0 but in some cases pagination might be started from other page */
    val initialPageNumber: Int get() = 0

    /** How many posts will be requested per page. Default value is 30  */
    val requestedPostsPerPageCount: Int get() = 30

}
