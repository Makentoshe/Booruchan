package com.makentoshe.booruchan.application.android.screen.booru.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.booru.BooruFragment
import toothpick.Toothpick
import toothpick.smoothie.lifecycle.closeOnDestroy

class BooruInjectingFragmentLifecycleCallback(): FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        when (f) {
            is BooruFragment -> injectBooruFragment(f)
        }
    }

    private fun injectBooruFragment(fragment: BooruFragment) {
        val module = BooruModule(fragment)
        val scope = Toothpick.openScopes(ApplicationScope::class, BooruScope::class)
        scope.installModules(module).closeOnDestroy(fragment).inject(fragment)
    }
}