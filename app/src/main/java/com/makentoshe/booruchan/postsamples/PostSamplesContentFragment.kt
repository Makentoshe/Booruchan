package com.makentoshe.booruchan.postsamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.AppActivity
import com.makentoshe.booruchan.ImageInternalCache
import com.makentoshe.booruchan.PostInternalCache
import com.makentoshe.booruchan.PreviewsInternalCache
import com.makentoshe.booruchan.postsamples.view.PostSamplesContentUi
import com.makentoshe.repository.CachedRepository
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository
import org.jetbrains.anko.AnkoContext

class PostSamplesContentFragment : Fragment() {

    private lateinit var viewModel: PostSamplesContentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val pagePosition = arguments!!.getInt(PAGEPOSITION)
        val itemPosition = arguments!!.getInt(ITEMPOSITION)
        val startDownloadController = arguments!!.get(DWNLDCNTRLLR) as StartDownloadRxController
        val booru = arguments!!.get(BOORU) as Booru

        val postsCache = PostInternalCache(requireContext(), "posts")
        val postsRepository = CachedRepository(postsCache, PostsRepository(booru))

        val imageInternalCache = ImageInternalCache(requireContext(), "samples")
        val samplesRepository = CachedRepository(imageInternalCache, SampleImageRepository(booru))

        val permissionChecker = (requireActivity() as AppActivity).permissionChecker
        val snackbarNotificationController = (requireActivity() as AppActivity).snackbarNotificationController

        val factory = PostSamplesContentViewModel.Factory(
            pagePosition,
            itemPosition,
            postsRepository,
            samplesRepository,
            startDownloadController,
            permissionChecker,
            snackbarNotificationController
        )
        viewModel = ViewModelProviders.of(this, factory)[PostSamplesContentViewModel::class.java]

        super.onCreate(savedInstanceState)
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

