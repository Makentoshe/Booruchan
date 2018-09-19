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

    @Gallery
    private var gallery = GALLERY_POST_ORD_INF

    override fun createView(ui: AnkoContext<BooruActivity>): View = with(ui) {
        val viewModel =  ViewModelProviders.of(ui.owner)[BooruViewModel::class.java]
        drawerLayout {
            createContentView(viewModel, ui)
            createPanelView()
            lparams(width = matchParent, height = matchParent)
        }
    }

    private fun _DrawerLayout.createPanelView() {
        frameLayout {
            backgroundResource = style.backgroundColor
            textView {
                text = "Panel"
            }
        }.lparams {
            width = dip(260)
            height = matchParent
            gravity = Gravity.START
        }
    }

    private fun _DrawerLayout.createContentView(viewModel: BooruViewModel, ui: AnkoContext<BooruActivity>) {
        constraintLayout {
            createSearchViewLayout(viewModel)
            createToolbar(viewModel)
                    .setSupportActionBar(ui.owner)
                    .setHomeIcon(style.toolbarForegroundColor, ui.owner)
                    .setHamburgerIcon(ui.owner, this@createContentView)
            createGallery(viewModel, ui)
            createSearchViewAlpha(ui, viewModel)

        }.lparams(matchParent, matchParent)
    }

    private fun _ConstraintLayout.createGallery(viewModel: BooruViewModel, ui: AnkoContext<BooruActivity>) {
        val gallery =  GalleryFactory
                .createFactory(gallery, viewModel.booru)
                .createGallery(ui.owner)
        frameLayout {
            gallery.createView(this, viewModel)
            viewModel.addSearchTermObserver(ui.owner, gallery.onSearchStarted())
        }.lparams {
            width = 0
            height = 0
            leftToLeft = PARENT_ID
            rightToRight = PARENT_ID
            topToBottom = R.id.booru_content_toolbar
            bottomToBottom = PARENT_ID
        }
    }

    @SuppressLint("NewApi")
    private fun _ConstraintLayout.createToolbar(viewModel: BooruViewModel): Toolbar {
        return toolbar {
            id = R.id.booru_content_toolbar
            setTitleTextColor(ContextCompat.getColor(context, style.toolbarForegroundColor))
            setSubtitleTextColor(ContextCompat.getColor(context, style.toolbarForegroundColor))
            title = viewModel.booru.getBooruName()
            subtitle = "Posts"
            backgroundColorResource = style.toolbarBackgroundColor
            forLollipop {
                elevation = dip(4).toFloat()
            }
        }.lparams {
            width = 0
            height = dip(style.dpToolbarHeight)
            leftToLeft = PARENT_ID
            rightToRight = PARENT_ID
            topToTop = PARENT_ID
        }
    }

    @SuppressLint("NewApi")
    private fun _ConstraintLayout.createSearchViewLayout(viewModel: BooruViewModel): LinearLayout {
        return linearLayout {
            id = R.id.booru_content_search
            visibility = View.GONE
            backgroundResource = style.toolbarBackgroundColor
            translationY = -dip(style.dpToolbarHeight).toFloat()
            forLollipop {
                elevation = dip(4).toFloat()
            }
            createSearchViewInputLayout(viewModel)

        }.lparams {
            width = matchConstraint
            height = dip(style.dpToolbarHeight)
            leftToLeft = PARENT_ID
            rightToRight = PARENT_ID
            topToBottom = R.id.booru_content_toolbar
        }
    }

    @SuppressLint("NewApi")
    private fun _LinearLayout.createSearchViewInputLayout(viewModel: BooruViewModel) {
        cardView {
            background.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            preventCornerOverlap = false
            radius = dip(4).toFloat()
            forLollipop {
                elevation = dip(0.5f).toFloat()
            }

            constraintLayout {
                createDelayAutocompleteEditText(viewModel)
                        .setClearIcon(createClearIconForDelayAutocompleteEditText(), style)
                        .setProgressBar(createProgressBarForDelayAutocompleteEditText())

            }.lparams(matchParent, matchParent)

        }.lparams(width = matchParent, height = matchParent) {
            margin = dip(7)
        }
    }

    private fun _ConstraintLayout.createDelayAutocompleteEditText(viewModel: BooruViewModel): DelayAutocompleteEditText {
        return delayAutocompleteEditText {
            id = R.id.booru_toolbar_search_delayautocompleteedittext
            hintResource = R.string.search_hint
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            inputType = EditorInfo.TYPE_CLASS_TEXT
            singleLine = true
            setPadding(dip(3), 0, dip(37), 0)
            setAdapter(viewModel.getAutocompleteAdapter(context, viewModel.booru))
            setActionSearch(viewModel)
        }.lparams(matchConstraint, matchConstraint) {
            leftToLeft = PARENT_ID
            rightToRight = PARENT_ID
            topToTop = PARENT_ID
            bottomToBottom = PARENT_ID
        }
    }

    private fun _ConstraintLayout.createClearIconForDelayAutocompleteEditText(): ImageView {
        return imageView {
            visibility = View.GONE
            id = R.id.booru_toolbar_search_clear
            padding = dip(4)
        }.lparams(dip(36), dip(36)) {
            setMargins(0, dip(6), dip(4), dip(10))
            bottomToBottom = PARENT_ID
            topToTop = PARENT_ID
            endToEnd = PARENT_ID
        }
    }

    private fun _ConstraintLayout.createProgressBarForDelayAutocompleteEditText(): ProgressBar {
        return progressBar {
            visibility = View.GONE
            id = R.id.booru_toolbar_search_progressbar
            isIndeterminate = true
        }.lparams(dip(36), dip(36)) {
            setMargins(0, dip(6), dip(4), dip(10))
            bottomToBottom = PARENT_ID
            topToTop = PARENT_ID
            endToEnd = PARENT_ID
        }
    }

    private fun _ConstraintLayout.createSearchViewAlpha(ui: AnkoContext<BooruActivity>, viewModel: BooruViewModel): FrameLayout {
        return frameLayout {
            id = R.id.booru_content_alpha
            visibility = View.GONE
            alpha = 1f
            backgroundResource = android.R.color.black

            onClick {
                viewModel.hideSearchLabel(ui.owner, style)
            }

        }.lparams {
            width = 0
            height = 0
            leftToLeft = PARENT_ID
            rightToRight = PARENT_ID
            topToBottom = R.id.booru_content_toolbar
            bottomToBottom = PARENT_ID
        }
    }

    private inline fun ViewManager.delayAutocompleteEditText(init: DelayAutocompleteEditText.() -> Unit) = ankoView({ DelayAutocompleteEditText(it) }, 0, init)

}
