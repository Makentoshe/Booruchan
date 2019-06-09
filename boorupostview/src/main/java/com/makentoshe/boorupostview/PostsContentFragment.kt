package com.makentoshe.boorupostview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipGroup
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.*
import com.makentoshe.api.AutocompleteRepository
import com.makentoshe.api.NetworkExecutorBuilder
import com.makentoshe.api.Repository
import com.makentoshe.api.TagRepository
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.style.ChipFactory
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.dip
import java.io.Serializable

class PostsContentFragment : Fragment() {

    private val panelLayout: SlidingUpPanelLayout by lazy {
        val panelLayoutId = com.makentoshe.boorupostview.R.id.slidingPanel
        return@lazy requireActivity().findViewById<SlidingUpPanelLayout>(panelLayoutId)
    }

    private val presenter: PostsContentFragmentPresenter by lazy {
        val networkExecutor = NetworkExecutorBuilder().build()
        val autocompleteRepository = AutocompleteRepository(booru, networkExecutor)
        val tagRepository = TagRepository(booru)
        PostsContentFragmentRxPresenterImpl(disposables, autocompleteRepository, tagRepository, panelLayout, tags)
    }

    private val disposables = CompositeDisposable()

    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsContentFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val searchbutton = view.findViewById<View>(R.id.search_button)
        presenter.bindSearchButton(searchbutton)

        val edittext = view.findViewById<AutoCompleteTextView>(com.makentoshe.boorupostview.R.id.search_edit_text)
        presenter.bindEditText(edittext)

        val chipGroup = view.findViewById<ChipGroup>(com.makentoshe.boorupostview.R.id.search_chip_group)
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

class PostsContentFragmentRxPresenterImpl(
    override val disposables: CompositeDisposable,
    private val autocompleteRepository: AutocompleteRepository,
    private val tagRepository: Repository<String, Tag>,
    private val panelLayout: SlidingUpPanelLayout,
    initialTagSet: Set<Tag> = emptySet()
) : PostsContentFragmentRxPresenter() {

    override val tags = HashSet<Tag>().apply { addAll(initialTagSet) }

    private val searchbuttonClickObserver = PublishSubject.create<Unit>().apply {
        val disposable = subscribe {
            panelLayout.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            //start search
            println("Search")
        }
        disposables.add(disposable)
    }

    private val tagsObservable = PublishSubject.create<Tag>()

    override fun bindSearchButton(view: View) {
        view.clicks().safeSubscribe(searchbuttonClickObserver)
    }

    override fun bindEditText(view: AutoCompleteTextView) {
        /* Observable for item click events */
        val itemClickObservable = setItemClickObservable(view)
        /* Observable for ime action events */
        val imeActionObservable = setEditorActionObservable(view)
        /* Observable for text changes */
        val textChangeObservable = setTextChangeObserver(view)
        /* Observable for tags adding into the search list */
        PublishSubject.create<Tag>().mergeWith(itemClickObservable).mergeWith(imeActionObservable)
            .mergeWith(textChangeObservable).doOnNext { view.setText("") }.safeSubscribe(tagsObservable)
        /* Set tag title autocomplete adapter */
        view.setAdapter(AutocompleteAdapter(autocompleteRepository))
    }

    private fun setItemClickObservable(view: AutoCompleteTextView): Observable<Tag> {
        return PublishSubject.create<AdapterViewItemClickEvent>().also {
            view.itemClickEvents().safeSubscribe(it)
        }.map { event -> event.view.getItemAtPosition(event.position) as Tag }
    }

    private fun setEditorActionObservable(view: AutoCompleteTextView): Observable<Tag> {
        return PublishSubject.create<TextViewEditorActionEvent>().also {
            view.editorActionEvents().safeSubscribe(it)
        }.filter { event -> event.actionId == EditorInfo.IME_ACTION_SEARCH }
            .map { event -> tagRepository.get(event.view.text.toString().trim())!! }
            .doAfterNext { searchbuttonClickObserver.onNext(Unit) }
    }

    private fun setTextChangeObserver(view: AutoCompleteTextView): Observable<Tag> {
        return PublishSubject.create<TextViewAfterTextChangeEvent>().also {
            view.afterTextChangeEvents().safeSubscribe(it)
        }.filter { it.editable?.isNotBlank() ?: false }
            .filter { event -> event.editable!!.lastOrNull() == ' ' }
            .map { event -> tagRepository.get(event.editable!!.toString().trim()) }
    }

    override fun bindChipGroup(view: ChipGroup) {
        tagsObservable.subscribe { tag -> view.createChip(tag) }.also { disposables.add(it) }
        tags.forEach { tag -> tagsObservable.onNext(tag)}
    }

    private fun ChipGroup.createChip(tag: Tag) {
        //add tag to the tag set
        tags.add(tag)
        //create chip view
        ChipFactory().build(context).apply {
            setChipSpacing(dip(4))
            text = tag.title
            isCloseIconVisible = true
        }.also(::addView).setOnCloseIconClickListener {
            removeView(it)
            tags.remove(tag)
        }
    }
}

