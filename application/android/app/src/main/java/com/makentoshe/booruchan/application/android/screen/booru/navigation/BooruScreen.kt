package com.makentoshe.booruchan.application.android.screen.booru.navigation

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.application.android.screen.booru.BooruFragment
import com.makentoshe.booruchan.core.context.BooruContext
import ru.terrakok.cicerone.android.support.SupportAppScreen

class BooruScreen(private val booruContext: BooruContext) : SupportAppScreen() {

    override fun getFragment(): Fragment {
        return BooruFragment.build(booruContext)
    }
}