package com.makentoshe.booruchan.boors.entity

import java.io.Serializable

abstract class User: Serializable {

    lateinit var username: String

    lateinit var role: String

    lateinit var joined: String

    lateinit var postsCount: String

    lateinit var deletedPostsCount: String

    lateinit var favoritesCount: String

    lateinit var commentsCount: String

    lateinit var tagEditsCount: String

    lateinit var noteEditsCount: String

    lateinit var forumPostsCount: String

    lateinit var recordCount: String

}
