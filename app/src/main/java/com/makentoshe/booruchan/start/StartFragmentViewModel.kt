package com.makentoshe.booruchan.start

import android.content.Context
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.BooruScreen
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.FragmentViewModel
import com.makentoshe.booruchan.SettingsScreen
import ru.terrakok.cicerone.Router

class StartFragmentViewModel(
    private val router: Router,
    private val booruList: List<Booru>
) : FragmentViewModel() {

    fun onOverflowMenuClicked(item: MenuItem): Boolean {
        when (item.itemId) {
            com.makentoshe.booruchan.R.id.settings -> {
                router.navigateTo(SettingsScreen())
                return true
            }
        }
        return false
    }

    fun onListItemClicked(booru: Booru) = router.newChain(BooruScreen(booru))

    fun getBooruListAdapter(context: Context): ListAdapter {
        val boorusTitles = Array(booruList.size) { booruList[it].title }
        return ArrayAdapter(context, android.R.layout.simple_list_item_1, boorusTitles)
    }
}