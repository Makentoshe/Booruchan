package com.makentoshe.booruchan.start

import android.content.Context
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.BooruScreen
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.SettingsScreen

class StartFragmentViewModel: ViewModel() {

    fun onOverflowMenuClicked(item: MenuItem): Boolean {
        when(item.itemId) {
            com.makentoshe.booruchan.R.id.settings ->
                Booruchan.INSTANCE.router.navigateTo(SettingsScreen())
        }
        return true
    }

    fun onListItemClicked(booru: Booru) {
        Booruchan.INSTANCE.router.newChain(BooruScreen(booru))
    }

    fun getBooruListAdapter(context: Context): ListAdapter {
        val boorus = Booruchan.INSTANCE.booruList
        val boorusTitles = Array(boorus.size) { boorus[it].title }
        return ArrayAdapter(context, android.R.layout.simple_list_item_1, boorusTitles)
    }
}