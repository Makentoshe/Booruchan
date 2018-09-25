package com.makentoshe.booruchan.booru.view.content

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.constraint.ConstraintSet
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.BooruViewModel
import com.makentoshe.booruchan.booru.ContentViewModel
import com.makentoshe.booruchan.booru.PanelViewModel
import com.makentoshe.booruchan.booru.model.content.factory.ContentFactory
import com.makentoshe.booruchan.booru.view.BooruActivity
import com.makentoshe.booruchan.common.StyleableAnkoComponent
import com.makentoshe.booruchan.common.delayAutocompleteEditText
import com.makentoshe.booruchan.common.forLollipop
import com.makentoshe.booruchan.common.styles.Style
import com.makentoshe.booruchan.common.view.DelayAutocompleteEditText
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4._DrawerLayout

class BooruActivityUIContent(style: Style,
                             private val booruViewModel: BooruViewModel,
                             private val contentViewModel: ContentViewModel,
                             private val panelViewModel: PanelViewModel,
                             private val dlContext: _DrawerLayout)
    : StyleableAnkoComponent<BooruActivity>(style) {

    override fun createView(ui: AnkoContext<BooruActivity>): View = with(dlContext) {
        constraintLayout {
            createSearchViewLayout()
            createToolbar(ui)
                    .setSupportActionBar(ui.owner)
                    .setHomeIcon(style.toolbarForegroundColor, ui.owner)
                    .setHamburgerIcon(ui.owner, dlContext)
            createGallery(ui)
            createSearchViewAlpha(ui)

        }.lparams(matchParent, matchParent)
    }

    private fun _ConstraintLayout.createGallery(ui: AnkoContext<BooruActivity>) {
        frameLayout {
            id = R.id.booru_content_container
            panelViewModel.addSelectedItemPositionObserver(ui.owner) { contentID ->
                println("New content: $contentID")
                val content = ContentFactory
                        .createFactory(contentID!!, contentViewModel.booru)
                        .createContent(ui.owner)
                val fragmentView = content.createView(contentViewModel)
                ui.owner.supportFragmentManager.beginTransaction()
                        .replace(R.id.booru_content_container, fragmentView)
                        .commitNow()
                contentViewModel.removeSearchTermObservers(ui.owner)
                contentViewModel.addSearchTermObserver(ui.owner, fragmentView.onSearchStarted().get()!!)
            }

        }.lparams {
            width = 0
            height = 0
            leftToLeft = ConstraintSet.PARENT_ID
            rightToRight = ConstraintSet.PARENT_ID
            topToBottom = R.id.booru_content_toolbar
            bottomToBottom = ConstraintSet.PARENT_ID
        }
    }

    @SuppressLint("NewApi")
    private fun _ConstraintLayout.createToolbar(ui: AnkoContext<BooruActivity>): Toolbar {
        return toolbar {
            id = R.id.booru_content_toolbar
            setTitleTextColor(ContextCompat.getColor(context, style.toolbarForegroundColor))
            setSubtitleTextColor(ContextCompat.getColor(context, style.toolbarForegroundColor))
            title = contentViewModel.booru.getBooruName()
            panelViewModel.addSelectedItemPositionObserver(ui.owner) {
                subtitle = contentViewModel.getSubtitleResByIndex(context, it!!)
            }
            backgroundColorResource = style.toolbarBackgroundColor
            forLollipop {
                elevation = dip(4).toFloat()
            }
        }.lparams {
            width = 0
            height = dip(style.dpToolbarHeight)
            leftToLeft = ConstraintSet.PARENT_ID
            rightToRight = ConstraintSet.PARENT_ID
            topToTop = ConstraintSet.PARENT_ID
        }
    }

    @SuppressLint("NewApi")
    private fun _ConstraintLayout.createSearchViewLayout(): LinearLayout {
        return linearLayout {
            id = R.id.booru_content_search
            visibility = View.GONE
            backgroundResource = style.toolbarBackgroundColor
            translationY = -dip(style.dpToolbarHeight).toFloat()
            forLollipop {
                elevation = dip(4).toFloat()
            }
            createSearchViewInputLayout()

        }.lparams {
            width = matchConstraint
            height = dip(style.dpToolbarHeight)
            leftToLeft = ConstraintSet.PARENT_ID
            rightToRight = ConstraintSet.PARENT_ID
            topToBottom = R.id.booru_content_toolbar
        }
    }

    @SuppressLint("NewApi")
    private fun _LinearLayout.createSearchViewInputLayout() {
        cardView {
            background.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            preventCornerOverlap = false
            radius = dip(4).toFloat()
            forLollipop {
                elevation = dip(0.5f).toFloat()
            }

            constraintLayout {
                createDelayAutocompleteEditText()
                        .setClearIcon(createClearIconForDelayAutocompleteEditText(), style)
                        .setProgressBar(createProgressBarForDelayAutocompleteEditText())

            }.lparams(matchParent, matchParent)

        }.lparams(width = matchParent, height = matchParent) {
            margin = dip(7)
        }
    }

    private fun _ConstraintLayout.createDelayAutocompleteEditText(): DelayAutocompleteEditText {
        return delayAutocompleteEditText {
            id = R.id.booru_toolbar_search_delayautocompleteedittext
            hintResource = R.string.search_hint
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            inputType = EditorInfo.TYPE_CLASS_TEXT
            singleLine = true
            setPadding(dip(3), 0, dip(37), 0)
            setAdapter(contentViewModel.getAutocompleteAdapter(context))
            setActionSearch(contentViewModel, booruViewModel)
        }.lparams(matchConstraint, matchConstraint) {
            leftToLeft = ConstraintSet.PARENT_ID
            rightToRight = ConstraintSet.PARENT_ID
            topToTop = ConstraintSet.PARENT_ID
            bottomToBottom = ConstraintSet.PARENT_ID
        }
    }

    private fun _ConstraintLayout.createClearIconForDelayAutocompleteEditText(): ImageView {
        return imageView {
            visibility = View.GONE
            id = R.id.booru_toolbar_search_clear
            padding = dip(4)
        }.lparams(dip(36), dip(36)) {
            setMargins(0, dip(6), dip(4), dip(10))
            bottomToBottom = ConstraintSet.PARENT_ID
            topToTop = ConstraintSet.PARENT_ID
            endToEnd = ConstraintSet.PARENT_ID
        }
    }

    private fun _ConstraintLayout.createProgressBarForDelayAutocompleteEditText(): ProgressBar {
        return progressBar {
            visibility = View.GONE
            id = R.id.booru_toolbar_search_progressbar
            isIndeterminate = true
        }.lparams(dip(36), dip(36)) {
            setMargins(0, dip(6), dip(4), dip(10))
            bottomToBottom = ConstraintSet.PARENT_ID
            topToTop = ConstraintSet.PARENT_ID
            endToEnd = ConstraintSet.PARENT_ID
        }
    }

    private fun _ConstraintLayout.createSearchViewAlpha(ui: AnkoContext<BooruActivity>): FrameLayout {
        return frameLayout {
            id = R.id.booru_content_alpha
            visibility = View.GONE
            alpha = 1f
            backgroundResource = android.R.color.black

            onClick {
                booruViewModel.hideSearchLabel(ui.owner, style)
            }

        }.lparams {
            width = 0
            height = 0
            leftToLeft = ConstraintSet.PARENT_ID
            rightToRight = ConstraintSet.PARENT_ID
            topToBottom = R.id.booru_content_toolbar
            bottomToBottom = ConstraintSet.PARENT_ID
        }
    }

}