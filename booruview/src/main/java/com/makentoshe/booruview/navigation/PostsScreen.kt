package com.makentoshe.booruview.navigation

import androidx.fragment.app.Fragment
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.PostsFragment
import com.makentoshe.navigation.Screen

class PostsScreen(private val booru: Booru, private val tags: Set<Tag>) : Screen() {

    override val fragment: Fragment
        get() = PostsFragment.build(booru, tags)
}
