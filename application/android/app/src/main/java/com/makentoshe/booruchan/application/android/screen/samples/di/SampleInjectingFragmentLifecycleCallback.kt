package com.makentoshe.booruchan.application.android.screen.samples.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.samples.SampleAnimationFragment
import com.makentoshe.booruchan.application.android.screen.samples.SampleContentFragment
import com.makentoshe.booruchan.application.android.screen.samples.SampleImageFragment
import com.makentoshe.booruchan.application.android.screen.samples.SamplePageFragment
import toothpick.Toothpick
import toothpick.smoothie.lifecycle.closeOnDestroy

class SampleInjectingFragmentLifecycleCallback : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) = when (f) {
        is SamplePageFragment -> injectSamplePageFragment(f)
        is SampleContentFragment -> injectSampleContentFragment(f)
        is SampleImageFragment -> injectSampleImageFragment(f)
        is SampleAnimationFragment -> injectSampleAnimationFragment(f)
        else -> Unit
    }

    private fun injectSamplePageFragment(fragment: SamplePageFragment) {
        val module = SamplePageModule(fragment)
        val scope = Toothpick.openScopes(ApplicationScope::class, SamplePageScope::class)
        scope.installModules(module).closeOnDestroy(fragment).inject(fragment)
    }

    private fun injectSampleContentFragment(fragment: SampleContentFragment) {
        val module = SampleContentModule(fragment)
        val scope = Toothpick.openScopes(SamplePageScope::class, SampleContentScope::class)
        scope.installModules(module).closeOnDestroy(fragment).inject(fragment)
    }

    private fun injectSampleImageFragment(fragment: SampleImageFragment) {
        val module = SampleImageModule(fragment)
        val scope = Toothpick.openScopes(SampleContentScope::class, SampleImageScope::class)
        scope.installModules(module).closeOnDestroy(fragment).inject(fragment)
    }

    private fun injectSampleAnimationFragment(fragment: SampleAnimationFragment) {
        val module = SampleAnimationModule(fragment)
        val scope = Toothpick.openScopes(SampleContentScope::class, SampleAnimationScope::class)
        scope.installModules(module).closeOnDestroy(fragment).inject(fragment)
    }
}
