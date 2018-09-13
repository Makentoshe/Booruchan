package com.makentoshe.booruchan.booru.view

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.common.StyleableAnkoComponent
import com.makentoshe.booruchan.booru.presenter.BooruPresenter
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
import org.jetbrains.anko.support.v4.drawerLayout

class BooruActivityUI(style: Style, private val presenter: BooruPresenter)
    : StyleableAnkoComponent<BooruActivity>(style), SlideableSearchLayout {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var searchView: View
    private lateinit var searchViewAlpha: View
    private lateinit var searchLayoutAnimator: SearchLayoutAnimator

    override fun createView(ui: AnkoContext<BooruActivity>): View = with(ui) {
        drawerLayout = drawerLayout {
            lparams(width = matchParent, height = matchParent)
            createContentView(ui)
            createPanelView()
        }
        setupHamburgerIcon(ui)
        return@with drawerLayout
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

    private fun _DrawerLayout.createContentView(ui: AnkoContext<BooruActivity>) {
        constraintLayout {
            createSearchView()
            createToolbar()
            createSearchViewAlpha()
            searchLayoutAnimator = SearchLayoutAnimator(searchView, searchViewAlpha, ui.owner, style)
        }.lparams(matchParent, matchParent)
    }

    private fun _ConstraintLayout.createToolbar() {
        toolbar = toolbar {
            id = R.id.activity_booru_toolbar
            setTitleTextColor(ContextCompat.getColor(context, style.toolbarForegroundColor))
            title = presenter.getBoor().getBooruName()
            backgroundColorResource = style.toolbarBackgroundColor
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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

    private fun _ConstraintLayout.createSearchView() {
        searchView = linearLayout {
            id = R.id.activity_booru_toolbar_search
            visibility = View.GONE
            backgroundResource = style.toolbarBackgroundColor
            translationY = -dip(style.dpToolbarHeight).toFloat()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                elevation = dip(4).toFloat()
            }

            cardView {
                background.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                preventCornerOverlap = false
                radius = dip(4).toFloat()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    elevation = dip(0.5f).toFloat()
                }

                include<ConstraintLayout>(R.layout.delay_autocomplete_edit_text)
                        .findViewById<DelayAutocompleteEditText>(R.id.DelayAutocompleteEditText)
                        .init(style).setActionSearch(presenter.getBoor())
                        .setAdapter(presenter.getAutocompleteAdapter())

            }.lparams(width = matchParent, height = matchParent) {
                margin = dip(7)
            }

        }.lparams {
            width = matchConstraint
            height = dip(style.dpToolbarHeight)
            leftToLeft = PARENT_ID
            rightToRight = PARENT_ID
            topToBottom = R.id.activity_booru_toolbar
        }
    }

    private fun _ConstraintLayout.createSearchViewAlpha() {
        searchViewAlpha = frameLayout {
            id = R.id.activity_booru_toolbar_search_alpha
            visibility = View.GONE
            alpha = 1f
            backgroundResource = android.R.color.black

            onClick {
                hideSearchLayout()
            }

        }.lparams {
            width = 0
            height = 0
            leftToLeft = PARENT_ID
            rightToRight = PARENT_ID
            topToBottom = R.id.activity_booru_toolbar
            bottomToBottom = PARENT_ID
        }
    }

    private fun setupHamburgerIcon(ui: AnkoContext<BooruActivity>) {
        ui.owner.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            val toggle = ActionBarDrawerToggle(ui.owner, drawerLayout, toolbar,
                    R.string.drawer_open, R.string.drawer_close)
            toggle.isDrawerIndicatorEnabled = true
            toggle.drawerArrowDrawable.color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ui.ctx.resources.getColor(style.toolbarForegroundColor, theme)
            } else {
                ui.ctx.resources.getColor(style.toolbarForegroundColor)
            }
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
        }
    }

    override fun showSearchLayout() {
        searchLayoutAnimator.show()
    }

    override fun hideSearchLayout(): Boolean {
        return searchLayoutAnimator.hide()
    }

    override fun isLayoutDisplaying(): Boolean {
        return searchLayoutAnimator.isDisplaying()
    }

}
