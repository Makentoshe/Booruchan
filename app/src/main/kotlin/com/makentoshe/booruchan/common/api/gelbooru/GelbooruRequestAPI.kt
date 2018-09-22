package com.makentoshe.booruchan.common.api.gelbooru

import com.makentoshe.booruchan.common.api.BoorRequestAPI
import java.io.Serializable

class GelbooruRequestAPI: BoorRequestAPI, Serializable {
    override fun getListOfCommentsViewRequest(page: Int): String {
        return getCustomRequest("index.php?page=comment&s=list&pid=$page")
    }

    override fun getListOfLastCommentsRequest(): String {
        return getCustomRequest("index.php?page=dapi&s=comment&q=index")
    }

    override fun getPostViewByIdRequest(id: Int): String {
        return getCustomRequest("index.php?page=post&s=view&id=$id")
    }

    override fun getCustomRequest(request: String): String {
        return "https://gelbooru.com/$request"
    }

    override fun getPostByIdRequest(id: Int): String {
        return getCustomRequest("index.php?page=dapi&q=index&s=post&id=$id")
    }

    override fun getPostsByTagsRequest(limit: Int, tags: String, page: Int): String {
        return getCustomRequest(
                "index.php?page=dapi&q=index&s=post&limit=$limit&tags=$tags&pid=$page")
    }

    override fun getAutocompleteSearchRequest(term: String): String {
        return getCustomRequest("index.php?page=autocomplete&term=$term")
    }

    override fun getCommentByPostIdRequest(id: Int): String {
        return getCustomRequest("index.php?page=dapi&s=comment&q=index&post_id=$id")
    }

    override fun getUserProfileViewByIdRequest(id: String): String {
        return getCustomRequest("index.php?page=account&s=profile&id=$id")
    }

}