package com.makentoshe.booruchan.application.android.screen.samples.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.samples.SampleFragment
import toothpick.Toothpick
import toothpick.smoothie.lifecycle.closeOnDestroy

class SampleInjectingFragmentLifecycleCallback : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        when (f) {
            is SampleFragment -> injectSampleFragment(f)
        }
    }

    private fun injectSampleFragment(fragment: SampleFragment) {
        val module = SampleModule(fragment)
        val scope = Toothpick.openScopes(ApplicationScope::class, SampleScope::class)
        scope.installModules(module).closeOnDestroy(fragment).inject(fragment)
    }
}
