package com.makentoshe.booruchan.postsamplespagepreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.ImageRepository
import com.makentoshe.booruchan.ViewModelFactory

class PostSamplePagePreviewFragment: Fragment() {

    private lateinit var viewModel: PostSamplePagePreviewFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = buildViewModel(arguments!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(context).apply {
            text = "preview ${viewModel.position}"
        }
    }

    private fun buildViewModel(arguments: Bundle): PostSamplePagePreviewFragmentViewModel {
        val sampleRepository = arguments.getSerializable(ImageRepository::class.java.simpleName) as ImageRepository
        val position = arguments.getInt(Int::class.java.simpleName)

        val factory = ViewModelFactory(sampleRepository = sampleRepository, position = position)
        return ViewModelProviders.of(this, factory)[PostSamplePagePreviewFragmentViewModel::class.java]
    }
}