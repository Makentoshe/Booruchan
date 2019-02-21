package com.makentoshe.booruchan.postsamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.postsamples.model.NavigationController
import com.makentoshe.booruchan.postsamples.model.ViewPagerAdapterBuilder
import com.makentoshe.booruchan.postsamples.view.PostSamplesUi
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

class PostSamplesFragment : androidx.fragment.app.Fragment() {

    /* Performs file downloading into external storage*/
    private lateinit var downloadFileViewModel: DownloadFileViewModel
    /* Performs any navigation */
    private lateinit var navigationController: NavigationController

    private lateinit var adapterBuilder: ViewPagerAdapterBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        val booru = arguments!!.get(BOORU) as Booru
        val tags = arguments!!.get(TAGS) as Set<Tag>
        val position = arguments!!.getInt(POSITION)
        adapterBuilder = PostSamplesVerticalViewPagerAdapterBuilder(booru, tags, position)

        val permissionChecker = (requireActivity() as AppActivity).permissionChecker
        val snackbarNotificationController = (requireActivity() as AppActivity).snackbarNotificationController
        val factory = DownloadFileViewModel.Factory(booru, tags, permissionChecker, snackbarNotificationController)
        downloadFileViewModel = ViewModelProviders.of(this, factory)[DownloadFileViewModel::class.java]

        val router = Booruchan.INSTANCE.router
        navigationController = NavigationControllerImpl(router)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        downloadFileViewModel.onCreateView(this)
        return PostSamplesUi(adapterBuilder, downloadFileViewModel, navigationController).createView(
            AnkoContext.create(
                requireContext(),
                this
            )
        )
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        private const val POSITION = "Position"

        fun create(booru: Booru, tags: Set<Tag>, position: Int): androidx.fragment.app.Fragment {
            return PostSamplesFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BOORU, booru)
                    putSerializable(TAGS, tags as Serializable)
                    putInt(POSITION, position)
                }
            }
        }
    }
}