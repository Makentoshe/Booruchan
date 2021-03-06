package com.makentoshe.booruchan.screen.search

import android.content.Intent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.core.util.Consumer
import androidx.fragment.app.DialogFragment
import com.google.android.material.chip.ChipGroup
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.model.RequestCode
import com.makentoshe.booruchan.view.addTagToChipGroup
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onEditorAction
import org.jetbrains.anko.sdk27.coroutines.textChangedListener

class SearchDialogEditTextInflater(
    private val initialSet: Set<Tag>,
    private val dialog: DialogFragment
) : Consumer<View> {

    val tags = HashSet<Tag>().apply { addAll(initialSet) }

    private fun EditText.setTextChanged(action: (Tag) -> Unit) {
        textChangedListener {
            afterTextChanged {
                if (text.isNotBlank() && text.last() == ' ') {
                    action(Tag.create(getTagTitle()))
                    setText("")
                }
            }
        }
    }

    private fun EditText.setImeAction(action: (Tag) -> Unit) {
        onEditorAction { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (text.isNotBlank()) {
                    action(Tag.create(getTagTitle()))
                    setText("")
                }
                val intent = Intent().putExtra(Set::class.java.simpleName, tags)
                dialog.targetFragment?.onActivityResult(RequestCode.search, 1, intent)
                dialog.dismiss()
            }
        }
    }

    override fun accept(view: View) {
        val editText = view.find<AutoCompleteTextView>(R.id.searchDialog_delayAutocompleteEditText)
        val chipGroup = view.find<ChipGroup>(R.id.searchDialog_chipgroup)
        //add tag after ' ' symbol
        editText.setTextChanged {
            //already contains the element
            if (!tags.add(it)) return@setTextChanged
            chipGroup.addTag(it)
        }
        //add tag after search action
        editText.setImeAction {
            //already contains the element
            if (!tags.add(it)) return@setImeAction
            chipGroup.addTag(it)
        }
        editText.onItemSelectedListener
        //add tag after clicking on the list item
        editText.setOnItemClickListener { parent, view, position, id ->
            editText.setText("")
            val tag = parent.adapter.getItem(position) as Tag
            //already contains the element
            if (!tags.add(tag)) return@setOnItemClickListener
            chipGroup.addTag(tag)
        }

        //add all tags we have
        tags.forEach { chipGroup.addTag(it) }
    }

    private fun ChipGroup.addTag(tag: Tag) {
        var tag = tag
        addTagToChipGroup(tag).apply {
            isClickable = true
            isCloseIconVisible = true

            setOnCloseIconClickListener {
                removeView(this)
                tags.remove(tag)
            }

            setOnClickListener {
                val newTag = if (tag.title.firstOrNull() == '-') {
                    Tag.create(tag.title.substring(1))
                } else {
                    Tag.create('-'.plus(tag.title))
                }
                tags.remove(tag)
                text = newTag.title
                tags.add(newTag)
                tag = newTag
            }
        }
    }

    companion object {
        private fun EditText.getTagTitle() = text.toString().replace(" ", "")
    }
}