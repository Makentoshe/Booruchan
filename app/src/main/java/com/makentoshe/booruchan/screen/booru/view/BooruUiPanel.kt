package com.makentoshe.booruchan.screen.booru.view

import android.view.Gravity
import android.view.View
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4._DrawerLayout

class BooruUiPanel : AnkoComponent<_DrawerLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<_DrawerLayout>): View =
        with(ui.owner) {
        verticalLayout {
            id = R.id.booru_drawer_panel
            backgroundColorResource = style.background.backgroundColorRes

            button(R.string.posts) {
                id = R.id.booru_drawer_panel_posts
            }.lparams(width = matchParent)

            button(R.string.account) {
                id = R.id.booru_drawer_panel_account
            }.lparams(width = matchParent)

        }.lparams(height = matchParent, width = dip(240)) {
            gravity = Gravity.START
        }
    }
}