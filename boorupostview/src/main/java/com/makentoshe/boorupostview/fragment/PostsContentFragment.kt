package com.makentoshe.boorupostview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipGroup
import com.makentoshe.api.AutocompleteRepository
import com.makentoshe.api.NetworkExecutorBuilder
import com.makentoshe.api.TagRepository
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.presenter.PostsContentFragmentPresenter
import com.makentoshe.boorupostview.presenter.PostsContentFragmentRxPresenterImpl
import com.makentoshe.boorupostview.R
import com.makentoshe.boorupostview.view.PostsContentFragmentUi
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

class PostsContentFragment : Fragment() {

    private val panelLayout: SlidingUpPanelLayout by lazy {
        val panelLayoutId = R.id.slidingPanel
        return@lazy requireActivity().findViewById<SlidingUpPanelLayout>(panelLayoutId)
    }

    private val presenter: PostsContentFragmentPresenter by lazy {
        val networkExecutor = NetworkExecutorBuilder().build()
        val autocompleteRepository = AutocompleteRepository(booru, networkExecutor)
        val tagRepository = TagRepository(booru)
        PostsContentFragmentRxPresenterImpl(disposables, autocompleteRepository, tagRepository, panelLayout, tags)
    }

    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsContentFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val searchbutton = view.findViewById<View>(R.id.search_button)
        presenter.bindSearchButton(searchbutton)

        val edittext = view.findViewById<AutoCompleteTextView>(R.id.search_edit_text)
        presenter.bindEditText(edittext)

        val chipGroup = view.findViewById<ChipGroup>(R.id.search_chip_group)
        presenter.bindChipGroup(chipGroup)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val tags = HashSet<Tag>().apply { addAll(presenter.tags) }
        this.tags = tags
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun build(booru: Booru, tags: Set<Tag>): Fragment {
            val fragment = PostsContentFragment()
            fragment.booru = booru
            fragment.tags = tags
            return fragment
        }
    }
}
