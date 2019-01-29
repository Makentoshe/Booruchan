package com.makentoshe.booruchan.booru

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.*

class BooruFragmentViewModel(@JvmField val booru: Booru, private val tags: Set<Tag>) : ViewModel() {

    private val contentController = ContentScreenController()
    private val drawerController = _DrawerController()

    fun addDrawerListener(init: _DrawerController.DrawerListener.() -> Unit) {
        drawerController.addDrawerListener(init)
    }

    fun openDrawer() = drawerController.newState(DrawerState.DrawerOpen)

    fun closeDrawer() = drawerController.newState(DrawerState.DrawerClose)

    fun onPostsClicked() {
        contentController.newScreen(newPostsScreen())
    }

    fun onAccountClicked() {
        contentController.newScreen(AccountScreen(booru, DrawerController(drawerController)))
    }

    override fun onCleared() {
        super.onCleared()
        contentController.clear()
    }

    fun update(fragmentActivity: FragmentActivity, fragmentManager: FragmentManager) {
        val navigator = Navigator(fragmentActivity, R.id.boorucontent, fragmentManager)
        contentController.update(navigator, newPostsScreen())
        drawerController.update()
    }

    private fun newPostsScreen(): PostsScreen {
        return PostsScreen(booru, DrawerController(drawerController), tags.toHashSet())
    }
}