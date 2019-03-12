package com.makentoshe.booruchan.screen.booru.inflator

import android.view.View
import androidx.core.util.Consumer
import com.makentoshe.booruchan.screen.booru.model.LocalNavigator

class BooruInflatorPanel(private val navigator: LocalNavigator) : Consumer<View> {

    override fun accept(view: View) {
        BooruInflatorPanelPosts(navigator).accept(view)
        BooruInflatorPanelAccount(navigator).accept(view)
    }
}