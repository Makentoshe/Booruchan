package com.makentoshe.booruchan.screen.posts.view

import android.graphics.PorterDuff
import android.util.TypedValue
import androidx.annotation.ColorInt
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style.style
import org.jetbrains.anko.*

class PostPageUiProgress : AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        themedProgressBar(style.progress) {
            id = R.id.posts_page_progress
            isIndeterminate = true

            val typedValue = TypedValue()
            val theme = context.theme
            theme.resolveAttribute(android.R.attr.indeterminateDrawable, typedValue, true)
            @ColorInt val color = typedValue.data
            indeterminateDrawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }.lparams {
            centerInParent()
        }
    }
}