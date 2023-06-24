package com.makentoshe.booruchan.application.android.screen.menu.navigation

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.application.android.screen.menu.MenuFragment
import com.makentoshe.booruchan.core.context.BooruContext
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MenuScreen(private val booruContext: BooruContext) : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return MenuFragment.build()
    }
}