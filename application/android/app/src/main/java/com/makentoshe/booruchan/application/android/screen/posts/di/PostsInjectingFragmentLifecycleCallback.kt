package com.makentoshe.booruchan.application.android.screen.posts.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.booru.BooruFragment
import com.makentoshe.booruchan.application.android.screen.posts.view.PostsFragment
import toothpick.Toothpick
import toothpick.smoothie.lifecycle.closeOnDestroy

class PostsInjectingFragmentLifecycleCallback: FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        when (f) {
            is PostsFragment -> injectPostsFragment(f)
        }
    }

    private fun injectPostsFragment(fragment: PostsFragment) {
        val module = PostsModule(fragment)
        val scope = Toothpick.openScopes(ApplicationScope::class, PostsScope::class)
        scope.installModules(module).closeOnDestroy(fragment).inject(fragment)
    }
}