package com.makentoshe.booruchan.application.android.start.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.start.StartFragment
import toothpick.Toothpick
import toothpick.smoothie.lifecycle.closeOnDestroy

class StartInjectingFragmentLifecycleCallback : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        println("Attached: $f")
        when (f) {
            is StartFragment -> injectStartFragment(f)
        }
    }

    private fun injectStartFragment(fragment: StartFragment) {
        val module = StartModule()
        val scope = Toothpick.openScopes(ApplicationScope::class, StartScope::class)
        scope.installModules(module).closeOnDestroy(fragment).inject(fragment)
    }


    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        println("Destroyed: $f")
    }
}