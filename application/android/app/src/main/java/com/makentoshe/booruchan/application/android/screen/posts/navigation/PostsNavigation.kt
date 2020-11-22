package com.makentoshe.booruchan.application.android.screen.posts.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.core.post.Post
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen

class PostsNavigation(private val router: Router) {

    fun navigateToPostScreen(post: Post) {
        router.navigateTo(SampleScreen())
    }
}

class SampleScreen(): SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return SampleFragment()
    }
}

class SampleFragment: CoreFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(requireContext()).apply { "SAS ASA ANUS PSA" }
    }
}