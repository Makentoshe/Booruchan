package com.makentoshe.booruchan.screen.search

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.google.android.material.chip.ChipGroup
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Inflater
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.chip
import com.makentoshe.booruchan.screen.arguments
import com.makentoshe.booruchan.screen.posts.model.TagsController
import com.makentoshe.booruchan.view.DelayAutocompleteEditText
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onEditorAction
import org.jetbrains.anko.sdk27.coroutines.textChangedListener

class SearchDialogFragment : DialogFragment() {

    private val disposables by lazy {
        CompositeDisposable()
    }

    private var tagsController: TagsController
        get() = arguments!!.get(TCTRL) as TagsController
        set(value) = arguments().putSerializable(TCTRL, value)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = SearchDialogUi().createView(AnkoContext.create(requireContext(), this))
        return AlertDialog.Builder(requireContext()).setTitle("Search by tags").setView(view).create()
            .also(::setParams)
            .also { onViewCreated(view, savedInstanceState) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        SearchDialogEditTextInflater(disposables, tagsController).inflate(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    private fun setParams(dialog: Dialog) = Unit

    companion object {
        private const val TCTRL = "TagsController"

        fun create(tagsController: TagsController) = SearchDialogFragment().apply {
            this.tagsController = tagsController
        }
    }
}

class SearchDialogEditTextInflater(
    private val disposables: CompositeDisposable,
    private val tagsController: TagsController
) : Inflater {

    class TextChanged(private val subject: Subject<Tag>) : Inflater {
        override fun inflate(view: View) {
            view.find<DelayAutocompleteEditText>(R.id.searchDialog_delayAutocompleteEditText).apply {
                textChangedListener {
                    afterTextChanged {
                        if (text.isNotBlank() && text.last() == ' ') {
                            subject.onNext(Tag(getTagTitle()))
                            setText("")
                        }
                    }
                }
            }
        }
    }

    class ImeActionClick(private val subject: Subject<Tag>) : Inflater {
        override fun inflate(view: View) {
            view.find<DelayAutocompleteEditText>(R.id.searchDialog_delayAutocompleteEditText).apply {
                onEditorAction { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH && text.isNotBlank()) {
                        subject.onNext(Tag(getTagTitle()))
                        clear()
                    }
                }
            }
        }
    }

    override fun inflate(view: View) {
        val subject = BehaviorSubject.create<Tag>()
        TextChanged(subject).inflate(view)
        ImeActionClick(subject).inflate(view)

        view.find<ChipGroup>(R.id.searchDialog_chipgroup).apply {
            tagsController.tags.forEach { addTagToChipGroup(it) }
            disposables.add(subject.subscribe { addTagToChipGroup(it) })
        }
    }

    private fun ChipGroup.addTagToChipGroup(tag: Tag) {
        tagsController.addTag(tag)
        chip {
            text = tag.name
        }
    }

    companion object {
        private fun EditText.getTagTitle() = text.toString().replace(" ", "")

        private fun EditText.clear() = setText("")
    }
}

