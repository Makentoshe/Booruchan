package com.makentoshe.booruchan.booru

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.Navigator
import com.makentoshe.booruchan.R

class BooruFragmentViewModel(@JvmField val booru: Booru) : ViewModel() {

    private val contentController = ContentController()

    fun onPostsClicked() {
//        contentController.newScreen(PostsScreen(booru))
    }

    fun onAccountClicked() {
//        contentController.newScreen(AccountScreen(booru))
    }

    override fun onCleared() {
        super.onCleared()
        contentController.clear()
    }

    fun update(fragmentActivity: FragmentActivity, fragmentManager: FragmentManager) {
        val navigator = Navigator(fragmentActivity, R.id.boorucontent, fragmentManager)
        contentController.update(navigator)
    }
}