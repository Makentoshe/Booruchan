package com.makentoshe.booruchan.start.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.common.StyleableAnkoComponent
import com.makentoshe.booruchan.common.styles.Style
import com.makentoshe.booruchan.start.StartViewModel
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.titleResource
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.sdk25.coroutines.onItemClick

class StartActivityUI(style: Style) : StyleableAnkoComponent<StartActivity>(style) {

    override fun createView(ui: AnkoContext<StartActivity>) = with(ui) {
        val viewModel = ViewModelProviders.of(ui.owner)[StartViewModel::class.java]
        verticalLayout {
            createToolbar()
                    .setSupportActionBar(ui.owner)
                    .setOverflowIconColor(style.toolbarForegroundColor)
            createContent(viewModel, ui.owner)
        }
    }

    private fun _LinearLayout.createToolbar(): Toolbar {
        return toolbar {
            setTitleTextColor(ContextCompat.getColor(context, style.toolbarForegroundColor))
            id = R.id.activity_start_toolbar
            titleResource = R.string.app_name
            backgroundColorResource = style.toolbarBackgroundColor
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                elevation = dip(4).toFloat()
            }
        }.lparams {
            width = matchParent
            height = dip(style.dpToolbarHeight)
        }
    }

    private fun _LinearLayout.createContent(viewModel: StartViewModel, activity: StartActivity) {
        listView {
            adapter = viewModel.createAdapter(context)
            onItemClick { adapter, _, position, _ ->
                val service = adapter?.getItemAtPosition(position) as String
                viewModel.clickOnService(activity, service)
            }
        }.lparams {
            width = matchParent
            height = matchParent
        }
    }

}