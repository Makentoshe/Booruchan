package com.makentoshe.booruchan.booru.view

import android.graphics.Color
import android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.BooruViewModel
import com.makentoshe.booruchan.common.StyleableAnkoComponent
import com.makentoshe.booruchan.common.styles.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint
import org.jetbrains.anko.support.v4._DrawerLayout
import android.graphics.Shader
import android.support.v4.content.ContextCompat
import android.view.ViewManager
import android.widget.ImageView.ScaleType
import com.makeramen.roundedimageview.RoundedImageView
import org.jetbrains.anko.custom.ankoView


class BooruActivityUIPanel(style: Style,
                           private val viewModel: BooruViewModel,
                           private val dlContext: _DrawerLayout)
    : StyleableAnkoComponent<BooruActivity>(style) {

    override fun createView(ui: AnkoContext<BooruActivity>): View = with(dlContext) {
        constraintLayout {
            backgroundResource = style.backgroundColor
            setOnClickListener {
                //listener for avoiding gesture control beyond the panel
            }

            createBackgroundFiller()
            createRoundedImageView()
            createServicesList()

        }.lparams {
            width = dip(260)
            height = matchParent
            gravity = Gravity.START
        }
    }

    private fun _ConstraintLayout.createBackgroundFiller() {
        imageView {
            id = R.id.booru_panel_background_image
            scaleType = ImageView.ScaleType.FIT_START
            adjustViewBounds = true
            if (viewModel.isUserLoggedIn()) {
                setImageDrawable(ContextCompat.getDrawable(context, R.drawable.kotlin))
            } else {
                //show something else
            }
        }.lparams(width = matchConstraint, height = wrapContent)

    }

    private fun _ConstraintLayout.createServicesList() {
        listView {
            adapter = viewModel.getServiceListAdapter(context)
        }.lparams(width = matchParent, height = wrapContent) {
            topToBottom = R.id.booru_panel_background_image
            startToStart = PARENT_ID
            endToEnd = PARENT_ID
        }
    }

    private fun _ConstraintLayout.createRoundedImageView() {
        roundedImageView {
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.kotlin))
            rotation = 137f
            scaleType = ScaleType.CENTER_CROP
            isOval = true
            tileModeX= Shader.TileMode.CLAMP
            tileModeY = Shader.TileMode.CLAMP
            cornerRadius = 10f

        }.lparams(dip(70), dip(70)) {
            bottomToBottom = R.id.booru_panel_background_image
            startToStart = PARENT_ID
            setMargins(dip(10), 0, 0, dip(10))
        }
    }

    private inline fun ViewManager.roundedImageView(init: RoundedImageView.() -> Unit) = ankoView({ RoundedImageView(it) }, 0, init)
}