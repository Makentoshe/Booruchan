package com.makentoshe.booruchan.booru

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.FragmentScreen
import com.makentoshe.booruchan.Navigator
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.account.AccountScreen
import com.makentoshe.booruchan.booru.model.ContentScreenController
import com.makentoshe.booruchan.booru.model.ContentScreenRxController
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.postpreviews.PostsFragment
import com.makentoshe.booruchan.postpreviews.PostsScreen
import com.makentoshe.viewmodel.ViewModel

/**
 * Class wraps [ContentScreenRxController] and realize [ContentScreenController] interface.
 */
class ContentScreenViewModel private constructor(
    private val contentScreenController: ContentScreenRxController
) : ViewModel(), ContentScreenController {
    /* Current selected booru instance where the user is */
    private lateinit var booru: Booru
    /* Performs drawer panel controlling and drawer events listening */
    private lateinit var drawerController: DrawerController
    /* Tags set for default searching. */
    private lateinit var tags: Set<Tag>

    /** Performs content screen perform to the PostsScreen */
    override fun onPostsClicked() = contentScreenController.action(newPostsScreen())

    /** Performs content screen perform to the AccountScreen */
    override fun onAccountClicked() = contentScreenController.action(newAccountScreen())

    /* Calls when the onCreateView called in the owner */
    override fun onCreateView(owner: Fragment) {
        val fragmentActivity = owner.requireActivity()
        val fragmentManager = owner.childFragmentManager

        val navigator = Navigator(fragmentActivity, R.id.booru_drawer_content, fragmentManager)
        contentScreenController.update(navigator, newPostsScreen())
    }

    /* Creates a new PostsScreen instance using ViewModel's properties*/
    private fun newPostsScreen(): FragmentScreen {
        return PostsScreen(PostsFragment.Arguments(booru, drawerController, tags))
    }

    /* Creates a new AccountScreen instance using ViewModel's properties */
    private fun newAccountScreen(): FragmentScreen {
        return AccountScreen(AccountScreen.Arguments(booru, drawerController))
    }

    /* Calls when the current ViewModel instance is no longer required */
    override fun onCleared() {
        super.onCleared()
        contentScreenController.clear()
    }

    /**
     * Factory for creating a new [ContentScreenViewModel] instance.
     *
     * @param booru a booru instance.
     * @param drawerController a drawerController instance.
     * @param tags a set of the tags for searching at the startup.
     */
    class Factory(
        private val booru: Booru,
        private val drawerController: DrawerController,
        private val tags: Set<Tag>
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = ContentScreenViewModel(ContentScreenRxController())

            viewModel.booru = booru
            viewModel.drawerController = drawerController
            viewModel.tags = tags

            return viewModel as T
        }
    }
}