package com.makentoshe.booruchan.application.android.analytic

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class AnalyticFragmentLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        println("Fragment attached: $f")
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        println("Fragment detached: $f")
    }
}