package com.makentoshe.booruchan.application.android.start

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class StartScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return StartFragment.build()
    }
}

