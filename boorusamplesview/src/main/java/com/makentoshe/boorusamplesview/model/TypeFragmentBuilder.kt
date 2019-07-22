package com.makentoshe.boorusamplesview.model

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.gifview.GifFragment
import com.makentoshe.imageview.ImageFragment
import com.makentoshe.webmview.WebmFragment
import java.io.File

class TypeFragmentBuilder(
    private val fragmentManager: FragmentManager,
    private val booru: Booru,
    private val position: Int
) {

    fun execute(post: Post, view: View) = when (File(post.sampleUrl).extension.toLowerCase()) {
        "webm" -> attachFragment(view.id) { WebmFragment.build(post.sampleUrl, position) }
        "gif" -> attachFragment(view.id) { GifFragment.build(booru, post) }
        else -> attachFragment(view.id) { ImageFragment.build(booru, post) }
    }

    /** Attaches the fragment to the [container] */
    private fun attachFragment(container: Int, factory: () -> Fragment) {
        val f = fragmentManager.findFragmentById(container)
        if (f != null) return else fragmentManager.beginTransaction().add(container, factory()).commit()
    }

}