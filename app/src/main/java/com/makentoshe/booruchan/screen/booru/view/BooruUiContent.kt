package com.makentoshe.booruchan.screen.booru.view

import android.view.View
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4._DrawerLayout

class BooruUiContent : AnkoComponent<_DrawerLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<_DrawerLayout>): View = with(ui.owner) {
        frameLayout {
            id = R.id.booru_drawer_content
            backgroundColorResource = style.background.backgroundColorRes
        }.lparams(matchParent, matchParent)
    }
}