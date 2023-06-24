package com.makentoshe.booruchan.application.android.screen.account.navigation

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.application.android.screen.account.AccountFragment
import com.makentoshe.booruchan.core.context.BooruContext
import ru.terrakok.cicerone.android.support.SupportAppScreen

class AccountScreen(private val booruContext: BooruContext) : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return AccountFragment.build()
    }
}