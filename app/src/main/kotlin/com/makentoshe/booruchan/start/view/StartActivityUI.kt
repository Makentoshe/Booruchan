package com.makentoshe.booruchan.start.view

import android.content.Context
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.StyleableAnkoComponent
import com.makentoshe.booruchan.start.presenter.StartActivityPresenter
import com.makentoshe.booruchan.styles.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.titleResource
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.sdk25.coroutines.onItemClick

class StartActivityUI(style: Style, private val presenter: StartActivityPresenter)
    : StyleableAnkoComponent<StartActivity>(style) {

    override fun createView(ui: AnkoContext<StartActivity>) = with(ui) {
        verticalLayout {
            createToolbar(ui.ctx, this) {
                ui.owner.setSupportActionBar(it)
            }
            setOverflowIconColor(style.toolbarTextColor, ui.owner)

            createListWithServices(this)
        }
    }

    private fun createToolbar(context: Context, llcontext: @AnkoViewDslMarker _LinearLayout,
                              then: (Toolbar) -> Unit) = with(llcontext) {
        then.invoke(toolbar {
            setTitleTextColor(ContextCompat.getColor(context, style.toolbarTextColor))
            id = R.id.activity_start_toolbar
            titleResource = R.string.app_name
            backgroundColorResource = style.toolbarBackgroundColor
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                elevation = dip(4).toFloat()
            }
        }.lparams {
            width = matchParent
            height = dip(style.dpToolbarHeight)
        })
    }

    private fun createListWithServices(llcontext: @AnkoViewDslMarker _LinearLayout) = with(llcontext) {
        listView {
            adapter = presenter.createAdapter()
            onItemClick { parent, view, position, id ->
                presenter.onServicesListItemClick(position)
            }
        }.lparams {
            width = matchParent
            height = matchParent
        }
    }
}