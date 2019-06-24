package com.makentoshe.boorupostview.presenter

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import com.google.android.material.chip.ChipGroup
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.*
import com.makentoshe.api.repository.Repository
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.NewSearchBroadcastReceiver
import com.makentoshe.boorupostview.model.AutocompleteAdapter
import com.makentoshe.style.ChipFactory
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.dip

/**
 * Reactive presenter component for posts search layout.
 *
 * @param autocompleteRepository returns a list of tags by term
 * @param tagRepository returns a tag instance by title
 */
class PostsContentFragmentRxPresenter(
    override val disposables: CompositeDisposable,
    private val autocompleteRepository: Repository<String, List<Tag>>,
    private val tagRepository: Repository<String, Tag>,
    context: Context, initialTagSet: Set<Tag> = emptySet()
) : RxPresenter(), PostsContentFragmentPresenter {

    /** Contains a tags */
    private val tags = HashSet<Tag>().apply { addAll(initialTagSet) }

    /** Observer for a search button click event */
    private val searchbuttonClickObserver = PublishSubject.create<Unit>().apply {
        //start search using broadcast on click event
        subscribe { NewSearchBroadcastReceiver.sendBroadcast(context, tags) }.let(disposables::add)
    }

    /** Observer for adding a tag to the view */
    private val tagObservable = PublishSubject.create<Tag>()

    override fun bindSearchButton(view: View) = view.clicks().safeSubscribe(searchbuttonClickObserver)

    override fun bindEditText(view: AutoCompleteTextView) {
        /* Observable for item click events */
        val itemClickObservable = setItemClickObservable(view)
        /* Observable for ime action events */
        val imeActionObservable = setEditorActionObservable(view)
        /* Observable for text changes */
        val textChangeObservable = setTextChangeObserver(view)
        /* Observable for tags adding into the search list */
        PublishSubject.create<Tag>().mergeWith(itemClickObservable).mergeWith(imeActionObservable)
            .mergeWith(textChangeObservable).doOnNext { view.setText("") }.safeSubscribe(tagObservable)
        /* Set tag title autocomplete adapter */
        view.setAdapter(AutocompleteAdapter(autocompleteRepository))
    }

    /** Add tag event on dropdown list item click */
    private fun setItemClickObservable(view: AutoCompleteTextView): Observable<Tag> {
        return PublishSubject.create<AdapterViewItemClickEvent>().also {
            view.itemClickEvents().safeSubscribe(it)
        }.map { event -> event.view.getItemAtPosition(event.position) as Tag }
    }

    /** Add tag event and start search event on ime action click */
    private fun setEditorActionObservable(view: AutoCompleteTextView): Observable<Tag> {
        return PublishSubject.create<TextViewEditorActionEvent>().also {
            view.editorActionEvents().safeSubscribe(it)
        }.filter { event -> event.actionId == EditorInfo.IME_ACTION_SEARCH }
            .map { event -> tagRepository.get(event.view.text.toString().trim())!! }
            .doAfterNext { searchbuttonClickObserver.onNext(Unit) }
    }

    /** Add tag event on space symbol input */
    private fun setTextChangeObserver(view: AutoCompleteTextView): Observable<Tag> {
        return PublishSubject.create<TextViewAfterTextChangeEvent>().also {
            view.afterTextChangeEvents().safeSubscribe(it)
        }.filter { it.editable?.isNotBlank() ?: false }
            .filter { event -> event.editable!!.lastOrNull() == ' ' }
            .map { event -> tagRepository.get(event.editable!!.toString().trim()) }
    }

    override fun bindChipGroup(view: ChipGroup) {
        tagObservable.subscribe { tag -> view.createChip(tag) }.let(disposables::add)
        tags.forEach { tag -> tagObservable.onNext(tag) }
    }

    /** Add a chip associated to a tag */
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