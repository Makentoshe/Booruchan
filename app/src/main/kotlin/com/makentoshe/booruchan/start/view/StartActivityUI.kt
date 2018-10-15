package com.makentoshe.booruchan.start.view

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.Toolbar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.common.StyleableAnkoComponent
import com.makentoshe.booruchan.common.forLollipop
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
            id = R.id.start_main
            createToolbar()
                    .setSupportActionBar(ui.owner)
                    .setOverflowIconColor(style.toolbar.onPrimaryColorRes)
            createContent(viewModel, ui.owner)
        }
    }

    @SuppressLint("NewApi")
    private fun _LinearLayout.createToolbar(): Toolbar {
        return toolbar {
            setTitleTextColorResource(style.toolbar.onPrimaryColorRes)
            id = R.id.start_toolbar
            titleResource = R.string.app_name
            backgroundColorResource = style.toolbar.primaryColorRes
            forLollipop {
                elevation = dip(4).toFloat()
            }
        }.lparams {
            width = matchParent
            height = style.toolbar.getHeightInPixel(context)
        }
    }

    private fun _LinearLayout.createContent(viewModel: StartViewModel, activity: StartActivity) {
        listView {
            id = R.id.start_content
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