package com.makentoshe.booruchan.screen.search

import android.content.Intent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipGroup
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Inflater
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.chip
import com.makentoshe.booruchan.screen.posts.model.SearchController
import com.makentoshe.booruchan.screen.posts.model.TagsController
import com.makentoshe.booruchan.view.DelayAutocompleteEditText
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onEditorAction
import org.jetbrains.anko.sdk27.coroutines.textChangedListener
import java.io.Serializable

class SearchDialogEditTextInflater(
    private val disposables: CompositeDisposable,
    private val tagsController: TagsController,
    private val dialog: DialogFragment
) : Inflater {

    class TextChanged(private val subject: Subject<Tag>) :
        Inflater {
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

    class ImeActionClick(
        private val subject: Subject<Tag>,
        private val tagsController: TagsController,
        private val dialog: DialogFragment
    ) : Inflater {
        override fun inflate(view: View) {
            view.find<DelayAutocompleteEditText>(R.id.searchDialog_delayAutocompleteEditText).apply {
                onEditorAction { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        if (text.isNotBlank()) {
                            subject.onNext(Tag(getTagTitle()))
                            clear()
                        }
                        val intent = Intent().putExtra(Set::class.java.simpleName, tagsController.tags as Serializable)
                        dialog.targetFragment?.onActivityResult(SearchDialogFragment.SEARCH_CODE, 1, intent)
                        dialog.dismiss()
                    }
                }
            }
        }
    }

    override fun inflate(view: View) {
        val subject = BehaviorSubject.create<Tag>()
        TextChanged(subject).inflate(view)
        ImeActionClick(subject, tagsController, dialog).inflate(view)

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