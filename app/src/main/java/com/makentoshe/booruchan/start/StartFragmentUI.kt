package com.makentoshe.booruchan.start

import android.graphics.PorterDuff
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.PopupMenu
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.toolbarLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.titleResource
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.lang.Exception
import java.lang.IndexOutOfBoundsException

class StartFragmentUI(
    private val startFragmentViewModel: StartFragmentViewModel
) : AnkoComponent<StartFragment> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<StartFragment>) = with(ui) {
        relativeLayout {
            backgroundColorResource = style.background.backgroundColorRes
            toolbarLayout()
            listViewLayout()
        }
    }

    private fun _RelativeLayout.toolbarLayout() {
        relativeLayout {
            id = R.id.start_toolbar_container
            backgroundColorResource = style.toolbar.primaryColorRes
            elevation = dip(10).toFloat()
            toolbarLayout({
                id = R.id.start_toolbar_container_toolbar
                backgroundColorResource = style.toolbar.primaryColorRes
                setTitleTextColor(style.toolbar.getOnPrimaryColor(context))
                titleResource = R.string.app_name
            }, {
                id = R.id.start_toolbar_container_overflow_icon
                setImageResource(style.drawable.static.overflow)
                setColorFilter(style.toolbar.getOnPrimaryColor(context), PorterDuff.Mode.SRC_ATOP)
                setOnClickListener(::createOverflowMenu)
            })
        }.lparams(width = matchParent, height = wrapContent)
    }

    private fun createOverflowMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menu.add(Menu.NONE, R.id.settings, 1, view.context.getString(R.string.settings))
        popupMenu.setOnMenuItemClickListener(startFragmentViewModel::onOverflowMenuClicked)
        popupMenu.show()
    }

    private fun _RelativeLayout.listViewLayout() {
        listView {
            id = R.id.start_content_listview
            setOnItemClickListener(::onItemClick)
            adapter = startFragmentViewModel.getBooruListAdapter(context)
        }.lparams {
            below(R.id.start_toolbar_container)
        }
    }

    private fun onItemClick(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
        try {
            startFragmentViewModel.onListItemClicked(Booruchan.INSTANCE.booruList[position])
        } catch (e: IndexOutOfBoundsException) {
        }
    }
}