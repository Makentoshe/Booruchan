package com.makentoshe.booruchan.postsamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.AppActivity
import com.makentoshe.booruchan.PostInternalCache
import com.makentoshe.booruchan.postsamples.view.PostSamplesContentUi
import com.makentoshe.repository.CachedRepository
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository
import org.jetbrains.anko.AnkoContext

class PostSamplesContentFragment : com.makentoshe.booruchan.Fragment<PostSamplesContentViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val pagePosition = arguments!!.getInt(PAGEPOSITION)
        val itemPosition = arguments!!.getInt(ITEMPOSITION)
        val startDownloadController = arguments!!.get(DWNLDCNTRLLR) as StartDownloadRxController
        val booru = arguments!!.get(BOORU) as Booru

        val postsCache = PostInternalCache(requireContext(), "posts")
        val postsRepository = CachedRepository(postsCache, PostsRepository(booru))

        super.onCreate(savedInstanceState)
    }

    override fun buildViewModel(arguments: Bundle): PostSamplesContentViewModel {
        val position = arguments.getInt(Int::class.java.simpleName)
        val holderArguments = ArgumentsHolder[this::class.java.simpleName.plus(position)]

        val postsRepository = holderArguments!![PostsRepository::class.java.simpleName] as PostsRepository
        val samplesRepository = holderArguments[SampleImageRepository::class.java.simpleName] as SampleImageRepository
        val startDownloadController =
            holderArguments[StartDownloadRxController::class.java.simpleName] as StartDownloadRxController
        val permissionChecker = (requireActivity() as AppActivity).permissionChecker
        val snackbarNotificationController = (requireActivity() as AppActivity).snackbarNotificationController

        val factory =
            PostSamplesContentViewModel.Factory(
                position,
                postsRepository,
                samplesRepository,
                startDownloadController,
                permissionChecker,
                snackbarNotificationController
            )
        return ViewModelProviders.of(this, factory)[PostSamplesContentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostSamplesContentUi(viewModel)
            .createView(AnkoContext.create(requireContext(), this))
    }

    companion object {
        private const val ITEMPOSITION = "ItemPosition"
        private const val PAGEPOSITION = "PagePosition"
        private const val DWNLDCNTRLLR = "DownloadController"
        private const val BOORU = "Booru"

        fun create(
            pagePosition: Int,
            itemPosition: Int,
            startDownloadController: StartDownloadRxController,
            booru: Booru
        ): Fragment {
            return PostSamplesContentFragment().apply {
                arguments = Bundle().apply {
                    putInt(PAGEPOSITION, pagePosition)
                    putInt(ITEMPOSITION, itemPosition)
                    putSerializable(DWNLDCNTRLLR, startDownloadController)
                    putSerializable(BOORU, booru)
                }
            }
        }
    }

}

