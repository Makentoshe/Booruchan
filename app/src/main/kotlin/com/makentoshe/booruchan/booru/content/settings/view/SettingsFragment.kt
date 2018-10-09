package com.makentoshe.booruchan.booru.content.settings.view

import android.os.Bundle
import com.makentoshe.booruchan.booru.content.view.ContentFragment
import java.lang.ref.WeakReference

class SettingsFragment : ContentFragment() {

    override fun onSearchStarted(): WeakReference<(String) -> Unit> {
        return WeakReference<(String) -> Unit> {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Create ${this::class.java.simpleName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Destroy ${this::class.java.simpleName}")
    }
}