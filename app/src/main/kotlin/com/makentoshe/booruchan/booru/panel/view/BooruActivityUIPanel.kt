package com.makentoshe.booruchan.booru.panel.view

import android.graphics.PorterDuff
import android.graphics.Shader
import android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.panel.PanelViewModel
import com.makentoshe.booruchan.booru.view.BooruActivity
import com.makentoshe.booruchan.common.StyleableAnkoComponent
import com.makentoshe.booruchan.common.styles.Style
import com.makentoshe.booruchan.common.view.roundedImageView
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint
import org.jetbrains.anko.sdk25.coroutines.onItemClick
import org.jetbrains.anko.support.v4._DrawerLayout


class BooruActivityUIPanel(style: Style,
                           private val viewModel: PanelViewModel,
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
//                setImageDrawable(ContextCompat.getDrawable(context, R.drawable.kotlin))
            } else {
                //show something else
            }
        }.lparams(width = matchConstraint, height = wrapContent)

    }

    private fun _ConstraintLayout.createServicesList() {
        listView {
            adapter = viewModel.getServiceListAdapter(context, style)

            viewModel.setSelectedItemPositionToStart()
            onItemClick { _, view, pos, _ ->
                viewModel.onItemSelect(view!!, pos, this@listView, style)
            }

        }.lparams(width = matchParent, height = wrapContent) {
            topToBottom = R.id.booru_panel_background_image
            startToStart = PARENT_ID
            endToEnd = PARENT_ID
        }
    }

    private fun _ConstraintLayout.createRoundedImageView() {
        roundedImageView {
            backgroundResource = style.toolbarBackgroundColor
            mutateBackground(true)
            val imageDrawable = ContextCompat.getDrawable(context, style.avdFromMagnifyToCross)
            val filterColor = ContextCompat.getColor(context, style.toolbarForegroundColor)
            imageDrawable?.setColorFilter(filterColor, PorterDuff.Mode.SRC_ATOP)
            setImageDrawable(imageDrawable)
            scaleType = ScaleType.CENTER_CROP
            isOval = true
            rotation = 45f
            tileModeX = Shader.TileMode.CLAMP
            tileModeY = Shader.TileMode.CLAMP
            padding = dip(20)

        }.lparams(dip(70), dip(70)) {
            bottomToBottom = R.id.booru_panel_background_image
            startToStart = PARENT_ID
            setMargins(dip(10), 0, 0, dip(10))
        }
    }

}