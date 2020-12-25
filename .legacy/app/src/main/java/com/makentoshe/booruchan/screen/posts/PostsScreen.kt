package com.makentoshe.booruchan.screen.posts

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.navigation.Screen

class PostsScreen(
    private val booru: Booru,
    private val tags: Set<Tag>
) : Screen() {
    override val fragment: Fragment
        get() = PostsFragment.create(booru, tags)
}

