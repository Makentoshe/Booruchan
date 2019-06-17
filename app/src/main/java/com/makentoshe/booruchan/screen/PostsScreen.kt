package com.makentoshe.booruchan.screen

import androidx.fragment.app.Fragment
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.fragment.PostsFragment
import com.makentoshe.navigation.Screen

/** Class describes a posts screen */
class PostsScreen(private val booru: Booru, private val tags: Set<Tag>) : Screen() {

    /** Factory property creates a new [PostsFragment] instance each call */
    override val fragment: Fragment
        get() = PostsFragment.build(booru, tags)
}
