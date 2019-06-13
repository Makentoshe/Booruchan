package com.makentoshe.boorupostview.presenter

import android.content.Intent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import com.google.android.material.chip.ChipGroup
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.*
import com.makentoshe.api.AutocompleteRepository
import com.makentoshe.api.Repository
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.PostsFragmentBroadcastReceiver
import com.makentoshe.boorupostview.model.AutocompleteAdapter
import com.makentoshe.style.ChipFactory
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.dip

class PostsContentFragmentRxPresenterImpl(
    override val disposables: CompositeDisposable,
    private val autocompleteRepository: AutocompleteRepository,
    private val tagRepository: Repository<String, Tag>,
    private val panelLayout: SlidingUpPanelLayout,
    initialTagSet: Set<Tag> = emptySet()
) : PostsContentFragmentRxPresenter() {

    override val tags = HashSet<Tag>().apply { addAll(initialTagSet) }

    private val searchbuttonClickObserver = PublishSubject.create<Unit>().apply {
        //start search using broadcast
        val disposable = subscribe {
            panelLayout.panelState = SlidingUpPanelLayout.PanelState.EXPANDED

            val intent = Intent(PostsFragmentBroadcastReceiver.START_NEW_SEARCH)
            intent.putExtra(PostsFragmentBroadcastReceiver.START_NEW_SEARCH, tags)
            panelLayout.context.sendBroadcast(intent)
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
        tags.forEach { tag -> tagsObservable.onNext(tag) }
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