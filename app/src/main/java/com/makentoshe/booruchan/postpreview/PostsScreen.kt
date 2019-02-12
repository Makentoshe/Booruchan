package com.makentoshe.booruchan.postpreview

import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.FragmentScreen
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.postpreviews.PostsFragment

class PostsScreen(
    private val booru: Booru,
    private val drawerController: DrawerController,
    private val tags: Set<Tag> = HashSet()
) : FragmentScreen() {

    override val fragment: Fragment
        get() = PostsFragment.create(booru, drawerController, tags)
}