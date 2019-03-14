package com.makentoshe.booruchan.screen.posts.view

import android.graphics.PorterDuff
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style.style
import org.jetbrains.anko.*

class PostsUiToolbarSearch : AnkoComponent<RelativeLayout> {
    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui.owner) {
        themedFrameLayout(style.toolbar) {
            id = R.id.posts_toolbar_search
            lparams(dip(56), dip(56))

            themedImageView(style.toolbar) {
                setImageResource(R.drawable.avd_cross_magnify)
                val typedValue = TypedValue()
                val theme = context.theme
                theme.resolveAttribute(android.R.attr.textColor, typedValue, true)
                @ColorInt val color = typedValue.data
                setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            }.lparams(dip(24), dip(24)) {
                gravity = Gravity.CENTER
            }
        }
    }
}