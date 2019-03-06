package com.makentoshe.booruchan.screen.start.view

import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.screen.start.StartFragment
import org.jetbrains.anko.*

class StartFragmentUi : AnkoComponent<StartFragment> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<StartFragment>) = with(ui) {
        relativeLayout {
            backgroundColorResource = style.background.backgroundColorRes
            StartUiToolbar().createView(AnkoContext.createDelegate(this))
            StartUiContent().createView(AnkoContext.createDelegate(this))
        }
    }

//
//    private fun _RelativeLayout.listViewLayout() {
//        listView {
//            id = R.id.start_content_listview
//            setOnItemClickListener(::onItemClick)
//            adapter = startFragmentViewModel.getBooruListAdapter(context)
//        }.lparams {
//            below(R.id.start_toolbar_container)
//        }
//    }
//
//    private fun onItemClick(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
//        try {
//            startFragmentViewModel.onListItemClicked(Booruchan.INSTANCE.booruList[position])
//        } catch (e: IndexOutOfBoundsException) {
//        }
//    }
}

