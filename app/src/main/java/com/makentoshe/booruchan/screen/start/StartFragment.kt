package com.makentoshe.booruchan.screen.start

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.router
import com.makentoshe.booruchan.screen.start.inflator.StartUiInflatorContent
import com.makentoshe.booruchan.screen.start.model.StartScreenNavigator
import com.makentoshe.booruchan.screen.start.view.StartFragmentUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class StartFragment : Fragment() {

    private val navigator by lazy {
        StartScreenNavigator(router)
    }

    private val booruList by lazy {
        Booruchan.INSTANCE.booruList
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return StartFragmentUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val icon = view.find<View>(R.id.start_toolbar_overflow)
        icon.setOnClickListener(::onOverflowIconClick)

        StartUiInflatorContent(navigator, booruList).accept(view)
    }


    private fun onOverflowIconClick(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menu.add(Menu.NONE, R.id.settings, 1, view.context.getString(R.string.settings))
        popupMenu.setOnMenuItemClickListener(::onOverflowItemMenuClick)
        popupMenu.show()
    }

    private fun onOverflowItemMenuClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                navigator.navigateToSettingsScreen()
                return true
            }
        }
        return false
    }
}