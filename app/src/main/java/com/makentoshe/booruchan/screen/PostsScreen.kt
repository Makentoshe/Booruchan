package com.makentoshe.booruchan.screen

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.PostsFragmentNavigator
import com.makentoshe.boorupostview.fragment.PostsFragment
import com.makentoshe.navigation.Screen

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
class PostsFragmentNavigator(
    private val router: Router,
    private val navigator: BooruImageScreenNavigator
) : PostsFragmentNavigator {

    override fun navigateToImageFragment(position: Int, booru: Booru, tags: Set<Tag>, post: Post) {
        router.navigateTo(ImageScreen(navigator, position, booru, tags))
    }
}
