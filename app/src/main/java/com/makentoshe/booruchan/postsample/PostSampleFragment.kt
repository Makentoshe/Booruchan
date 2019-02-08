package com.makentoshe.booruchan.postsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import androidx.lifecycle.ViewModelProviders
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.Fragment
import com.makentoshe.booruchan.postsamples.ArgumentsHolder
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository
import com.makentoshe.style.Style
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.custom.ankoView
import kotlin.random.Random

class PostSampleFragment : Fragment<PostSampleViewModel>() {

    override fun buildViewModel(arguments: Bundle): PostSampleViewModel {
        val position = arguments.getInt(Int::class.java.simpleName)

        val holderArguments = ArgumentsHolder[this::class.java.simpleName.plus(position)]

        val postsRepository = holderArguments!![PostsRepository::class.java.simpleName] as PostsRepository
        val samplesRepository = holderArguments[SampleImageRepository::class.java.simpleName] as SampleImageRepository
        val factory = PostSampleViewModel.Factory(position, postsRepository, samplesRepository)
        return ViewModelProviders.of(this, factory)[PostSampleViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostSampleUi(viewModel).createView(AnkoContext.create(requireContext(), this))
    }
}

class PostSampleUi(
    private val viewModel: PostSampleViewModel,
    private val style: Style = Booruchan.INSTANCE.style
) : AnkoComponent<androidx.fragment.app.Fragment> {
    override fun createView(ui: AnkoContext<androidx.fragment.app.Fragment>): View = with(ui) {
        subsamplingScaleImageView {
            backgroundColor = Random.nextInt()
        }
    }

    private fun ViewManager.subsamplingScaleImageView(init: SubsamplingScaleImageView.() -> Unit): SubsamplingScaleImageView {
        return ankoView({ SubsamplingScaleImageView(it) }, 0, init)
    }
}