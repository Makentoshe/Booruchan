package com.makentoshe.booruchan.application.android.screen.samples.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.samples.SampleContentFragment
import com.makentoshe.booruchan.application.android.screen.samples.SampleImageFragment
import toothpick.Toothpick
import toothpick.smoothie.lifecycle.closeOnDestroy

class SampleInjectingFragmentLifecycleCallback : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        when (f) {
            is SampleContentFragment -> injectSampleFragment(f)
            is SampleImageFragment -> injectSampleImageFragment(f)
        }
    }

    private fun injectSampleImageFragment(fragment: SampleImageFragment) {
        val module = SampleImageModule(fragment)
        val scope = Toothpick.openScopes(ApplicationScope::class, SampleContentScope::class, SampleImageScope::class)
        scope.installModules(module).closeOnDestroy(fragment).inject(fragment)
    }

    private fun injectSampleFragment(fragment: SampleContentFragment) {
        val module = SampleContentModule(fragment)
        val scope = Toothpick.openScopes(ApplicationScope::class, SampleContentScope::class)
        scope.installModules(module).closeOnDestroy(fragment).inject(fragment)
    }
}
