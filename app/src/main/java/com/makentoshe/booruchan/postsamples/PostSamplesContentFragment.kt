package com.makentoshe.booruchan.postsamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.postsamples.model.SamplesContentViewPagerAdapter
import com.makentoshe.booruchan.postsamples.view.PostSamplesContentUi
import com.makentoshe.booruchan.postsamples.view.VerticalViewPager
import com.makentoshe.viewmodel.ViewModel
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import kotlin.random.Random

class PostSamplesContentFragment : com.makentoshe.booruchan.Fragment<PostSamplesContentViewModel>() {

    override fun buildViewModel(arguments: Bundle): PostSamplesContentViewModel {
        val position = arguments.getInt(Int::class.java.simpleName)
        val holderArguments = ArgumentsHolder[this::class.java.simpleName.plus(position)]

        val factory = PostSamplesContentViewModel.Factory(position, childFragmentManager)
        return ViewModelProviders.of(this, factory)[PostSamplesContentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostSamplesContentUi(viewModel)
            .createView(AnkoContext.create(requireContext(), this))
    }
}

class PostSamplesContentViewModel : ViewModel() {
    var position = 0
        private set
    lateinit var viewPagerAdapter: SamplesContentViewPagerAdapter
        private set

    class Factory(
        private val position: Int,
        private val fragmentManager: FragmentManager
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplesContentViewModel()
            viewModel.position = position
            viewModel.viewPagerAdapter = SamplesContentViewPagerAdapter(fragmentManager)
            return viewModel as T
        }
    }
}

class PostSampleFragment() : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View(context).apply {
            backgroundColor = Random.nextInt()
        }
    }
}