package com.makentoshe.booruchan.screen.sampleinfo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadListener
import com.makentoshe.booruchan.screen.sampleinfo.controller.SampleInfoViewController
import com.makentoshe.booruchan.screen.sampleinfo.SampleInfoViewModel
import com.makentoshe.booruchan.screen.sampleinfo.view.SampleInfoUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.Serializable

class SampleInfoFragment : Fragment() {

    private var itemPosition: Int
        get() = arguments!!.getInt(ITEM_ID)
        set(value) = arguments().putInt(ITEM_ID, value)

    private var position: Int
        get() = arguments!!.getInt(POSITION)
        set(value) = arguments().putInt(POSITION, value)

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private val disposables by inject<CompositeDisposable>()

    private val postsDownloadListener: PostsDownloadListener by viewModel<SampleInfoViewModel> {
        parametersOf(booru, tags, position, disposables)
    }

    private val viewController by inject<SampleInfoViewController> {
        parametersOf(view!!, postsDownloadListener, booru, itemPosition)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SampleInfoUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewController.bind(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    companion object {
        private const val ITEM_ID = "ItemId"
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        private const val POSITION = "Position"
        fun create(itemId: Int, booru: Booru, tags: Set<Tag>, position: Int) = SampleInfoFragment().apply {
            this.booru = booru
            this.tags = tags
            this.position = position
            this.itemPosition = when (itemId) {
                R.id.bottombar_commentsitem -> 2
                R.id.bottombar_tagsitem -> 1
                R.id.bottombar_infoitem -> 0
                else -> 0
            }
        }
    }
}
