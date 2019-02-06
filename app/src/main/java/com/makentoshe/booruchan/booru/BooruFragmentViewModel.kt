package com.makentoshe.booruchan.booru

import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.booru.model.ContentScreenController
import com.makentoshe.booruchan.booru.model.DrawerController

class BooruFragmentViewModel(@JvmField val booru: Booru, private val tags: Set<Tag>) : FragmentViewModel() {

    private val contentController = ContentScreenController()
    private val drawerController = DrawerController()

    fun getDrawerState() = drawerController.state

    fun addDrawerListener(init: DrawerController.DrawerListener.() -> Unit) {
        drawerController.subscribe(init)
    }

    fun openDrawer() = drawerController.openDrawer()

    fun closeDrawer() = drawerController.closeDrawer()

    fun onPostsClicked() {
        contentController.newScreen(newPostsScreen())
    }

    fun onAccountClicked() {
        contentController.newScreen(AccountScreen(booru, drawerController))
    }

    override fun onCleared() {
        super.onCleared()
        contentController.clear()
        drawerController.clear()
    }

    override fun onCreateView(owner: Fragment) {
        val fragmentActivity = owner.requireActivity()
        val fragmentManager = owner.childFragmentManager

        val navigator = Navigator(fragmentActivity, R.id.booru_drawer_content, fragmentManager)
        contentController.update(navigator, newPostsScreen())
        drawerController.clear()
    }

    private fun newPostsScreen(): PostsScreen {
        return PostsScreen(booru, drawerController, tags.toHashSet())
    }
}