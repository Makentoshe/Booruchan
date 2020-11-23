package com.makentoshe.booruchan.application.android.screen.samples.navigation

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.application.android.screen.samples.SampleFragment
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Post
import ru.terrakok.cicerone.android.support.SupportAppScreen

class SampleScreen(
    private val post: Post,
    private val booruContextClass: Class<BooruContext>
) : SupportAppScreen() {

    override fun getFragment(): Fragment {
        return SampleFragment.build(booruContextClass, post)
    }
}