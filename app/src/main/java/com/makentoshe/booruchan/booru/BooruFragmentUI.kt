package com.makentoshe.booruchan.booru

import android.view.Gravity
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4._DrawerLayout
import org.jetbrains.anko.support.v4.drawerLayout

class BooruFragmentUI(private val booruFragmentViewModel: BooruFragmentViewModel) :
    AnkoComponent<BooruFragment> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<BooruFragment>) = with(ui) {
        drawerLayout {
            id = R.id.boorudrawer
            createContent()
            createPanel()
        }
    }

    private fun _DrawerLayout.createContent() {
        frameLayout {
            id = R.id.boorucontent
            backgroundColorResource = style.background.backgroundColorRes
        }.lparams(matchParent, matchParent)
    }

    private fun _DrawerLayout.createPanel() {
        verticalLayout {
            backgroundColorResource = style.background.backgroundColorRes

            button(R.string.posts) {
                onClick {
                    booruFragmentViewModel.onPostsClicked()
                }
            }.lparams(width = matchParent)

            button(R.string.account) {
                onClick {
                    booruFragmentViewModel.onAccountClicked()
                }
            }.lparams(width = matchParent)

        }.lparams(height = matchParent, width = dip(240)) {
            gravity = Gravity.START
        }
    }
}