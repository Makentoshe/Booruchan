package com.makentoshe.booruchan.application.android.screen.posts.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.posts.view.PostsFragment
import com.makentoshe.booruchan.application.android.screen.search.PostsSearchFragment
import com.makentoshe.booruchan.application.android.screen.search.di.PostsSearchModule
import com.makentoshe.booruchan.application.android.screen.search.di.PostsSearchScope
import toothpick.Toothpick
import toothpick.smoothie.lifecycle.closeOnDestroy

class PostsInjectingFragmentLifecycleCallback: FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        when (f) {
            is PostsFragment -> injectPostsFragment(f)
            is PostsSearchFragment -> injectPostsSearchFragment(f)
        }
    }

    private fun injectPostsSearchFragment(fragment: PostsSearchFragment) {
        val module = PostsSearchModule(fragment)
        val scope = Toothpick.openScopes(ApplicationScope::class, PostsScope::class, PostsSearchScope::class.java)
        scope.installModules(module).closeOnDestroy(fragment).inject(fragment)
    }

    private fun injectPostsFragment(fragment: PostsFragment) {
        val module = PostsModule(fragment)
        val scope = Toothpick.openScopes(ApplicationScope::class, PostsScope::class)
        scope.installModules(module).closeOnDestroy(fragment).inject(fragment)
    }
}