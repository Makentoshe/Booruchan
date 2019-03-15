package com.makentoshe.booruchan.screen.booru.view

import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.drawerLayout
import org.jetbrains.anko.support.v4.themedDrawerLayout

class BooruUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        themedDrawerLayout(style.default) {
            id = R.id.booru_drawer
            BooruUiContent().createView(AnkoContext.createDelegate(this))
            BooruUiPanel().createView(AnkoContext.createDelegate(this))
        }
    }
}