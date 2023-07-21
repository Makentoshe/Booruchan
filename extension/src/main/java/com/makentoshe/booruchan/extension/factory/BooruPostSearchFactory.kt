package com.makentoshe.booruchan.extension.factory

import com.makentoshe.booruchan.extension.entity.NetworkPost
import com.makentoshe.booruchan.extension.parser.PostSearchParser
import com.makentoshe.booruchan.feature.NetworkRequest
import com.makentoshe.booruchan.feature.NetworkResponse
import com.makentoshe.booruchan.feature.text

abstract class BooruPostSearchFactory constructor(
    private val parser: PostSearchParser,
){
    /** Initial page number for api. Mostly it is 0 but in some cases pagination might be started from other page */
    val initialPageNumber: Int = 0

    /** How many posts will be requested per page. Default value is 30  */
    val requestedPostsPerPageCount: Int = 30

    /** How search tags should be separated */
    val searchTagSeparator: String = " "

    /** Creates a request for NetworkRepository which performs exact request to the booru */
    abstract fun buildRequest(request: FetchPostsRequest): NetworkRequest

    fun parseResponse(response: NetworkResponse): List<NetworkPost> {
        return parser.parse(response.content.text)
    }

    /**
     * Returns a list of rating tags that helps filtering
     *
     * Some boorus might not have them, like Safebooru. In this case return
     * an empty list and additional view will not be rendered for convenient
     * choosing.
     * */
    open fun getRatings(): List<Rating> = emptyList()

    data class FetchPostsRequest(
        // How many posts we want to retrieve. There might be a hard limit for posts per request.
        val count: Int,
        // The page number for pagination
        val page: Int,
        // The tags to search for
        val tags: String,
    )

    data class Rating(
        val tag: String, // describes main word for search engine,
        val value: String, // describes value of the rating, like safe, explicit, etc
    )

}
