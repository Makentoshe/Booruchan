package com.makentoshe.booruchan.postsamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.AppActivity
import com.makentoshe.booruchan.UnitRxController
import com.makentoshe.booruchan.postsamples.model.AdapterBuilder
import com.makentoshe.booruchan.postsamples.model.AdapterBuilderImpl
import com.makentoshe.booruchan.postsamples.view.PostSamplesContentUi
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

class PostSamplesContentFragment : Fragment() {

    private lateinit var viewModel: PostSamplesContentViewModel
    private lateinit var adapterBuilder: AdapterBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        val startDownloadController = arguments!!.get(DWNLDCNTRLLR) as UnitRxController
        val booru = arguments!!.get(BOORU) as Booru
        val tags = arguments!!.get(TAGS) as Set<Tag>
        val position = arguments!!.getInt(POSITION)
        val permissionChecker = (requireActivity() as AppActivity).permissionChecker
        val snackbarNotificationController = (requireActivity() as AppActivity).snackbarNotificationController

        val factory = PostSamplesContentViewModel.Factory(
            position,
            startDownloadController,
            permissionChecker,
            snackbarNotificationController
        )
        viewModel = ViewModelProviders.of(this, factory)[PostSamplesContentViewModel::class.java]

        adapterBuilder = AdapterBuilderImpl(booru, tags)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostSamplesContentUi(viewModel, adapterBuilder)
            .createView(AnkoContext.create(requireContext(), this))
    }

    companion object {
        private const val DWNLDCNTRLLR = "DownloadController"
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        private const val POSITION = "Position"
        fun create(
            startDownloadController: UnitRxController,
            booru: Booru,
            tags: Set<Tag>,
            position: Int
        ): Fragment {
            return PostSamplesContentFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(DWNLDCNTRLLR, startDownloadController)
                    putSerializable(BOORU, booru)
                    putSerializable(TAGS, tags as Serializable)
                    putInt(POSITION, position)
                }
            }
        }
    }
}

