package com.makentoshe.booruchan.application.android.screen.posts.navigation

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.application.android.screen.posts.PostsFragment
import com.makentoshe.booruchan.core.context.BooruContext
import ru.terrakok.cicerone.android.support.SupportAppScreen

class PostsScreen(private val booruContext: BooruContext) : SupportAppScreen() {

    override fun getFragment(): Fragment {
        return PostsFragment.build(booruContext.javaClass)
    }
}