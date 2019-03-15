package com.makentoshe.booruchan.screen.booru.view

import android.view.View
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style.style
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4._DrawerLayout

class BooruUiContent : AnkoComponent<_DrawerLayout> {

    override fun createView(ui: AnkoContext<_DrawerLayout>): View = with(ui.owner) {
        themedFrameLayout(style.main) {
            id = R.id.booru_drawer_content
        }.lparams(matchParent, matchParent)
    }
}