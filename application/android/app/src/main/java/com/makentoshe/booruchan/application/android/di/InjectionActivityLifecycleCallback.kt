package com.makentoshe.booruchan.application.android.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.makentoshe.booruchan.application.android.AppActivity
import toothpick.Toothpick

class InjectionActivityLifecycleCallback(
    private val injectingFragmentLifecycleCallback: InjectingFragmentLifecycleCallback
) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity !is AppActivity) return
        Toothpick.openScopes(ApplicationScope::class.java).inject(activity)
        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(injectingFragmentLifecycleCallback, true)
    }

    override fun onActivityStarted(activity: Activity?) = Unit
    override fun onActivityPaused(activity: Activity?) = Unit
    override fun onActivityResumed(activity: Activity?) = Unit
    override fun onActivityStopped(activity: Activity?) = Unit
    override fun onActivityDestroyed(activity: Activity?) = Unit
    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) = Unit
}

