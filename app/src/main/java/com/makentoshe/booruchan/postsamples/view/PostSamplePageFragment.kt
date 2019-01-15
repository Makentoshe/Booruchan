package com.makentoshe.booruchan.postsamples.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.VerticalViewPager
import com.makentoshe.booruchan.ViewModelFactory
import com.makentoshe.booruchan.postsamples.model.SamplePageController
import com.makentoshe.booruchan.postsamples.model.SamplePageContentPagerAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onPageChangeListener

class PostSamplePageFragment : Fragment() {

    private lateinit var viewModel: PostSamplePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = buildViewModel(arguments!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return PostSamplePageFragmentUi(viewModel).createView(AnkoContext.create(requireContext(), this))
    }

    private fun buildViewModel(arguments: Bundle): PostSamplePageViewModel {
        val position = arguments.getInt(Int::class.java.simpleName)
        val blockController =
            arguments.getSerializable(SamplePageController::class.java.simpleName) as SamplePageController

        val factory = ViewModelFactory(position = position, samplePageController = blockController)
        return ViewModelProviders.of(this, factory)[PostSamplePageViewModel::class.java]
    }
}

class PostSamplePageFragmentUi(
    private val viewModel: PostSamplePageViewModel
) : AnkoComponent<PostSamplePageFragment> {

    private lateinit var root: View

    override fun createView(ui: AnkoContext<PostSamplePageFragment>): View = with(ui) {
        frameLayout {
            root = this
            lparams(matchParent, matchParent)
            addView(VerticalViewPager(context).apply {
                id = R.id.content_viewpager
                adapter = viewModel.getViewPagerAdapter(ui.owner.childFragmentManager)
                currentItem = 1
                onPageChangeListener {
                    onPageSelected {
                        when (it) {
                            1 -> {
                                viewModel.unblock()
                                println("Can swap")
                            }
                            2 -> {
                                viewModel.block()
                                println("Cant swap")
                            }
                        }
                    }
                    val parentFragment = ui.owner.parentFragment
                    onPageScrolled { position, positionOffset, positionOffsetPixels ->
                        if (position == 0) {
                            parentFragment?.view?.alpha = positionOffset
                            if (positionOffset == 0f) viewModel.backToPreviews()
                        }
                    }
                }
            })
        }
    }
}

class PostSamplePageViewModel(
    private val position: Int,
    private val samplePageController: SamplePageController
) : ViewModel() {

    fun block() = samplePageController.block()

    fun unblock() = samplePageController.unblock()

    fun backToPreviews() = samplePageController.backToPreviews()

    fun getViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
        return SamplePageContentPagerAdapter(fragmentManager)
    }
}