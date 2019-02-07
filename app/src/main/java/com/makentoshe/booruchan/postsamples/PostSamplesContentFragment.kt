package com.makentoshe.booruchan.postsamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.postsamples.view.PostSamplesContentUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import kotlin.random.Random

class PostSamplesContentFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostSamplesContentUi()
            .createView(AnkoContext.create(requireContext(), this))
    }
}

class PostSampleFragment(): Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View(context).apply {
            backgroundColor = Random.nextInt()
        }
    }
}