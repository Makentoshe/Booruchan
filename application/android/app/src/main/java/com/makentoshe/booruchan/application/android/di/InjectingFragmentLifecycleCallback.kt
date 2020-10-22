package com.makentoshe.booruchan.application.android.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class InjectingFragmentLifecycleCallback : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        println("Attached: $f")
//        when (f) { }
    }


    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        println("Destroyed: $f")
    }
}