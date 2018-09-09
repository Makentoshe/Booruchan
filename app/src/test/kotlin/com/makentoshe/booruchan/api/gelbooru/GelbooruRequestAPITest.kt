package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.BooruRequestAPITest
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GelbooruRequestAPITest : BooruRequestAPITest(GelbooruRequestAPI()) {

    override fun getPostsByTagsRequestExpected(limit: Int, tags: String, page: Int): String {
        return "https://gelbooru.com/index.php?page=dapi&q=index&s=post&limit=$limit&tags=$tags&pid=$page"
    }

    override fun getPostViewByIdRequestExpected(id: Int): String {
        return "https://gelbooru.com/index.php?page=post&s=view&id=$id"
    }

    override fun getAutocompleteSearchRequestExpected(term: String): String {
        return "https://gelbooru.com/index.php?page=autocomplete&term=$term"
    }

    override fun getUserProfileViewByIdRequestExpected(id: String): String {
        return "https://gelbooru.com/index.php?page=account&s=profile&id=$id"
    }

    override fun getCommentByPostIdRequestExpected(id: Int): String {
        return "https://gelbooru.com/index.php?page=dapi&s=comment&q=index&post_id=$id"
    }

    override fun getPostByIdRequestExpected(id: Int): String {
        return "https://gelbooru.com/index.php?page=dapi&q=index&s=post&id=$id"
    }

    override fun getCustomRequestExpected(str: String): String {
        return "https://gelbooru.com/$str"
    }


}