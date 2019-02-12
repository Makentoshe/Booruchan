package com.makentoshe.booruchan.booru.view

import android.view.Gravity
import androidx.core.view.GravityCompat
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.BooruFragment
import com.makentoshe.booruchan.booru.model.ContentScreenController
import com.makentoshe.booruchan.booru.model.DrawerController
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4._DrawerLayout
import org.jetbrains.anko.support.v4.drawerLayout
import org.jetbrains.anko.support.v4.drawerListener

class BooruFragmentUI(
    private val contentScreenController: ContentScreenController,
    private val drawerController: DrawerController
) : AnkoComponent<BooruFragment> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<BooruFragment>) = with(ui) {
        drawerLayout {
            id = R.id.booru_drawer
            createContent()
            createPanel()
            drawerController.addDrawerListener {
                onClose { closeDrawer(GravityCompat.START) }
                onOpen { openDrawer(GravityCompat.START) }
            }
            drawerListener {
                onDrawerOpened { drawerController.openDrawer() }
                onDrawerClosed { drawerController.closeDrawer() }
            }
        }
    }

    private fun _DrawerLayout.createContent() {
        frameLayout {
            id = R.id.booru_drawer_content
            backgroundColorResource = style.background.backgroundColorRes
        }.lparams(matchParent, matchParent)
    }

    private fun _DrawerLayout.createPanel() {
        verticalLayout {
            id = R.id.booru_drawer_panel
            backgroundColorResource = style.background.backgroundColorRes

            button(R.string.posts) {
                id = R.id.booru_drawer_panel_posts
                onClick {
                    contentScreenController.onPostsClicked()
                }
            }.lparams(width = matchParent)

            button(R.string.account) {
                id = R.id.booru_drawer_panel_account
                onClick {
                    contentScreenController.onAccountClicked()
                }
            }.lparams(width = matchParent)

        }.lparams(height = matchParent, width = dip(240)) {
            gravity = Gravity.START
        }
    }
}