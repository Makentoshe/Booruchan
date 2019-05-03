package com.makentoshe.booruchan.screen.sampleinfo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.sampleinfo.controller.SampleInfoInfoViewController
import com.makentoshe.booruchan.screen.sampleinfo.view.SampleInfoInfoUi
import org.jetbrains.anko.AnkoContext
import org.koin.androidx.scope.currentScope
import org.koin.core.parameter.parametersOf

class SampleInfoInfoFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    private val viewController by currentScope.inject<SampleInfoInfoViewController> { parametersOf(view!!, post) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SampleInfoInfoUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewController.bind(this)
    }

    companion object {
        private const val BOORU = "Booru"
        private const val POST = "Post"
        fun create(booru: Booru, post: Post) = SampleInfoInfoFragment().apply {
            this.booru = booru
            this.post = post
        }
    }
}
