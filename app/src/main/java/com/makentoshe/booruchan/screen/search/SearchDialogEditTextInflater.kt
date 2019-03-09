package com.makentoshe.booruchan.screen.search

import android.content.Intent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Inflater
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.chip
import com.makentoshe.booruchan.screen.RequestCode
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onEditorAction
import org.jetbrains.anko.sdk27.coroutines.textChangedListener
import java.io.Serializable

class SearchDialogEditTextInflater(
    private val initialSet: Set<Tag>,
    private val dialog: DialogFragment
) : Inflater {

    private val tags = HashSet<Tag>().apply { addAll(initialSet) }

    private fun EditText.setTextChanged(action: (Tag) -> Unit) {
        textChangedListener {
            afterTextChanged {
                if (text.isNotBlank() && text.last() == ' ') {
                    action(Tag(getTagTitle()))
                    setText("")
                }
            }
        }
    }

    private fun EditText.setImeAction(action: (Tag) -> Unit) {
        onEditorAction { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (text.isNotBlank()) {
                    action(Tag(getTagTitle()))
                    setText("")
                }
                val intent = Intent().putExtra(Set::class.java.simpleName, tags)
                dialog.targetFragment?.onActivityResult(RequestCode.search, 1, intent)
                dialog.dismiss()
            }
        }
    }

    override fun inflate(view: View) {
        val editText = view.find<EditText>(R.id.searchDialog_delayAutocompleteEditText)
        val chipGroup = view.find<ChipGroup>(R.id.searchDialog_chipgroup)
        editText.setTextChanged {
            chipGroup.addTagToChipGroup(it)
        }
        editText.setImeAction {
            chipGroup.addTagToChipGroup(it)
        }
        tags.forEach { chipGroup.addTagToChipGroup(it) }
    }

    private fun ChipGroup.addTagToChipGroup(tag: Tag) {
        tags.add(tag)
        chip {
            text = tag.name
            isClickable = true
            isCloseIconVisible = true
            setOnCloseIconClickListener {
                for (i in 0 until childCount) {
                    val chip = getChildAt(i) as Chip
                    if (chip.text == tag.name) {
                        removeView(chip)
                        tags.remove(tag)
                    }
                }
            }
        }
    }

    companion object {
        private fun EditText.getTagTitle() = text.toString().replace(" ", "")
    }
}