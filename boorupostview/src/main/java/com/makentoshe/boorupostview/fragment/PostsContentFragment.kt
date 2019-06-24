package com.makentoshe.boorupostview.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipGroup
import com.makentoshe.api.repository.AutocompleteRepository
import com.makentoshe.api.NetworkExecutorBuilder
import com.makentoshe.api.repository.TagRepository
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.NewSearchBroadcastReceiver
import com.makentoshe.boorupostview.R
import com.makentoshe.boorupostview.presenter.PostsContentFragmentRxPresenter
import com.makentoshe.boorupostview.view.PostsContentFragmentUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

/**
 * Fragment for the content view in panel layout.
 * It contains selected tags, search text view and start search button.
 */
class PostsContentFragment : Fragment() {

    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    /** Broadcast receiver for receiving a new search events from another fragment */
    private val broadcastReceiver = NewSearchBroadcastReceiver()

    /** Contains a disposable which will be released on destroy lifecycle event */
    private val disposables = CompositeDisposable()

    /** Register receiver */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        NewSearchBroadcastReceiver.registerReceiver(requireActivity(), broadcastReceiver)
        // save tags to the args on new search event
        broadcastReceiver.onNewSearchStarted { tags = it }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return PostsContentFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // create presenter
        val networkExecutor = NetworkExecutorBuilder.buildSmartGet()
        val autocompleteRepository = AutocompleteRepository(booru, networkExecutor)
        val tagRepository = TagRepository(booru)
        val presenter = PostsContentFragmentRxPresenter(
            disposables, autocompleteRepository, tagRepository, requireContext(), tags
        )
        // bind search button
        val searchbutton = view.findViewById<View>(R.id.search_button)
        presenter.bindSearchButton(searchbutton)
        // bind search edit text
        val edittext = view.findViewById<AutoCompleteTextView>(R.id.search_edit_text)
        presenter.bindEditText(edittext)
        // bind chip group
        val chipGroup = view.findViewById<ChipGroup>(R.id.search_chip_group)
        presenter.bindChipGroup(chipGroup)
    }

    /** Unregister receiver */
    override fun onDetach() {
        super.onDetach()
        requireActivity().unregisterReceiver(broadcastReceiver)
    }

    /** Clear disposables */
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
