package com.makentoshe.booruchan

import android.graphics.PorterDuff
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.makentoshe.booruchan.postpreviews.view.setToolbarIcon
import com.makentoshe.style.Style
import org.jetbrains.anko.*


interface Inflater {
    fun inflate(view: View)
}

abstract class ToolbarIcon<T : ViewGroup>(protected val style: Style = Booruchan.INSTANCE.style) : AnkoComponent<T> {

    protected fun ImageView.setImage(style: Style, @DrawableRes icon: Int) {
        setImageResource(icon)
        setColorFilter(style.toolbar.getOnPrimaryColor(context), PorterDuff.Mode.SRC_ATOP)
    }
}

class BackToolbarIcon : ToolbarIcon<_RelativeLayout>() {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        frameLayout {
            id = R.id.toolbar_back
            imageView {
                setToolbarIcon(style, style.drawable.static.arrow)
                rotation = -90f
            }.lparams(dip(24), dip(24)) {
                gravity = Gravity.CENTER
            }
        }.lparams(dip(56), dip(56))
    }
}