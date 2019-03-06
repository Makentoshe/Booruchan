package com.makentoshe.booruchan.screen.booru

import android.view.View
import com.makentoshe.booruchan.screen.Inflator
import com.makentoshe.booruchan.screen.booru.model.LocalNavigator

class BooruInflatorPanel(private val navigator: LocalNavigator) :
    Inflator {

    override fun inflate(view: View) {
        BooruInflatorPanelPosts(navigator).inflate(view)
        BooruInflatorPanelAccount(navigator).inflate(view)
    }
}