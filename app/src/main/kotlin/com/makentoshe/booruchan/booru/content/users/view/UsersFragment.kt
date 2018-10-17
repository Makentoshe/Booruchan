package com.makentoshe.booruchan.booru.content.users.view

import android.os.Bundle
import com.makentoshe.booruchan.booru.content.view.ContentFragment
import java.lang.ref.WeakReference

class UsersFragment : ContentFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Create ${this::class.java.simpleName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Destroy ${this::class.java.simpleName}")
    }
}