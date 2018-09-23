package com.makentoshe.booruchan.booru.view.content

import android.os.Bundle
import android.support.v4.app.Fragment

class PostsFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Create ${this::class.java.simpleName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Destroy ${this::class.java.simpleName}")
    }
}