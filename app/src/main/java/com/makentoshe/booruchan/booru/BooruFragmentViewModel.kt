package com.makentoshe.booruchan.booru

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.account.AccountScreen
import com.makentoshe.booruchan.Navigator
import com.makentoshe.booruchan.postpreviews.PostsScreen
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.ContentScreenController
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.viewmodel.ViewModel

class BooruFragmentViewModel private constructor() : ViewModel() {
    private lateinit var booru: Booru
    private lateinit var tags: Set<Tag>
    private lateinit var contentController: ContentScreenController
    private lateinit var drawerController: DrawerController

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
        return PostsScreen(booru, drawerController, tags)
    }

    class Factory(private val booru: Booru, private val tags: Set<Tag>) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = BooruFragmentViewModel()

            viewModel.drawerController = DrawerController()
            viewModel.contentController = ContentScreenController()
            viewModel.booru = booru
            viewModel.tags = tags

            return viewModel as T
        }
    }
}