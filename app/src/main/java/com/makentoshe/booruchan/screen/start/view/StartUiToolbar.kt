package com.makentoshe.booruchan.screen.start.view

import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.updateLayoutParams
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.ToolbarIcon
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.titleResource
import org.jetbrains.anko.appcompat.v7.toolbar

class StartUiToolbar : AnkoComponent<_RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        relativeLayout {
            id = R.id.start_toolbar
            backgroundColorResource = style.toolbar.primaryColorRes
            elevation = dip(10).toFloat()
            toolbar {
                backgroundColorResource = style.toolbar.primaryColorRes
                setTitleTextColor(style.toolbar.getOnPrimaryColor(context))
                titleResource = R.string.app_name
            }.lparams(width = matchParent) { alignWithParent = true }

            val color = style.toolbar.getOnPrimaryColor(context)
            val icon = style.drawable.static.overflow
            ToolbarIcon(R.id.start_toolbar_overflow, icon, color)
                .createView(AnkoContext.createDelegate(this))
                .updateLayoutParams<RelativeLayout.LayoutParams> {
                    alignParentEnd()
                    centerVertically()
                }
        }
    }
}