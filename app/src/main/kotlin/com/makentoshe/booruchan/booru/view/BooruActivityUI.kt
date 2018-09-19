package com.makentoshe.booruchan.booru.view

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.annotation.IntDef
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.BooruViewModel
import com.makentoshe.booruchan.booru.model.gallery.factory.GalleryFactory
import com.makentoshe.booruchan.common.StyleableAnkoComponent
import com.makentoshe.booruchan.common.forLollipop
import com.makentoshe.booruchan.common.styles.Style
import com.makentoshe.booruchan.common.view.DelayAutocompleteEditText
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4._DrawerLayout
import org.jetbrains.anko.support.v4.drawerLayout

//todo fix UI decreased speed
class BooruActivityUI(style: Style) : StyleableAnkoComponent<BooruActivity>(style) {

    companion object {
        const val GALLERY_POST_ORD_INF = 0x00000000
        const val GALLERY_COMMENT = 0x10000000
    }

    @IntDef(GALLERY_POST_ORD_INF, GALLERY_COMMENT)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Gallery



    override fun createView(ui: AnkoContext<BooruActivity>): View = with(ui) {
        val viewModel = ViewModelProviders.of(ui.owner)[BooruViewModel::class.java]
        val view = drawerLayout {
            BooruActivityUIContent(style, viewModel, this).createView(ui)
            BooruActivityUIPanel(style, viewModel, this).createView(ui)
        }
        view.layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
        return@with view
    }



}
