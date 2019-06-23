package com.makentoshe.booruchan.screen

import androidx.fragment.app.Fragment
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.PostsFragmentNavigator
import com.makentoshe.boorupostview.fragment.PostsFragment
import com.makentoshe.navigation.Screen
import java.text.FieldPosition

/** Class describes a posts screen */
class PostsScreen(
    private val booru: Booru,
    private val tags: Set<Tag>,
    private val navigator: PostsFragmentNavigator
) : Screen() {

    /** Factory property creates a new [PostsFragment] instance each call */
    override val fragment: Fragment
        get() = PostsFragment.build(booru, tags, navigator)
}

/**
 * Class performs a navigation from the posts screen.
 */
class PostsFragmentNavigator : PostsFragmentNavigator {
    override fun navigateToSampleFragment(page: Int, position: Int, total: Int) {
        val concretePosition = total * page + position
        println(concretePosition)
    }
}