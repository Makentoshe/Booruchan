package com.makentoshe.booruchan.screen.search

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.RequestCode
import com.makentoshe.booruchan.model.TagsHolder
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.repository.stream.StreamRepositoryFactory
import com.makentoshe.booruchan.view.DelayAutocompleteEditText
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onEditorAction
import org.jetbrains.anko.sdk27.coroutines.textChangedListener
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.io.Serializable

class SearchDialogFragment : DialogFragment() {

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private lateinit var viewController: SearchDialogViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //after recreation the tags of tags will be saved
        //in the bundle, so just restore them into global var
        //note: tags from the bundle also contains the original tags
        if (savedInstanceState != null) {
            val tags = savedInstanceState.getSerializable(TAGS) as Set<Tag>
            this.tags = tags
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = SearchDialogUi().createView(AnkoContext.create(requireContext(), this))
        return AlertDialog.Builder(requireContext()).setTitle(R.string.start_new_search).setView(view).create()
            .also(::setParams)
            .also { onViewCreated(view, savedInstanceState) }
    }

    /**
     * This method will not being called by default because the [onCreateDialog]
     * method calls instead [onCreateView].
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewController = SearchDialogViewController(view, booru, HashSet(tags))
        viewController.bind(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(TAGS, viewController.tags as Serializable)
    }

    private fun setParams(dialog: Dialog) {
        dialog.window?.setGravity(Gravity.TOP)
    }

    companion object {
        private const val TAGS = "Tags"
        private const val BOORU = "Booru"
        fun create(tags: Set<Tag>, booru: Booru) = SearchDialogFragment().apply {
            this.tags = tags
            this.booru = booru
        }
    }
}

class SearchDialogViewController(
    root: View, booru: Booru,
    override val tags: MutableSet<Tag>
) : KoinComponent, TagsHolder {

    private val repositoryFactory by inject<StreamRepositoryFactory> {
        parametersOf(booru, null)
    }

    private val editText = root.find<DelayAutocompleteEditText>(R.id.searchDialog_delayAutocompleteEditText)
    private val progressbar = root.find<ProgressBar>(R.id.search_progress)
    private val chipGroup = root.find<ChipGroup>(R.id.searchDialog_chipgroup)

    private val tagChipBuilder by lazy { TagChipBuilder(chipGroup.context) }

    fun bind(fragment: Fragment) {
        fragment as DialogFragment

        val repository = repositoryFactory.buildAutocompleteRepository()
        editText.setAdapter(DelayAutocompleteAdapter(repository))
        editText.progressBar = this.progressbar

        tags.forEach(::addTagToTheView)

        editText.textChangedListener {
            afterTextChanged {
                if (it!!.isNotBlank() && it.last() == ' ') {
                    val tagTitle = it.toString().replace(" ", "")
                    val tag = Tag.create(tagTitle)
                    updateTags(tag)
                    editText.setText("")
                }
            }
        }

        editText.onEditorAction { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (editText.text.isNotBlank()) {
                    val tagTitle = editText.text.toString().replace(" ", "")
                    val tag = Tag.create(tagTitle)
                    updateTags(tag)
                    editText.setText(" ")
                }

                val intent = Intent().putExtra(Set::class.java.simpleName, tags as Serializable)
                fragment.targetFragment?.onActivityResult(RequestCode.search, 1, intent)
                fragment.dismiss()
            }
        }

        editText.setOnItemClickListener { parent, _, position, _ ->
            val tag = parent.adapter.getItem(position) as Tag
            updateTags(tag)
            editText.setText("")
        }

    }

    private fun updateTags(tag: Tag) {
        //if tag already exists - return
        if (!tags.add(tag)) return
        addTagToTheView(tag)
    }

    private fun addTagToTheView(tag: Tag) {
        var tag = tag
        val chip = tagChipBuilder.build(tag)

        chip.setOnCloseIconClickListener {
            chipGroup.removeView(chip)
            tags.remove(tag)
        }

        chip.setOnClickListener {
            val newTag = if (tag.title.firstOrNull() == '-')
                Tag.create(tag.title.substring(1))
            else
                Tag.create('-'.plus(tag.title))

            tags.remove(tag)
            chip.text = newTag.title
            tags.add(newTag)
            tag = newTag
        }

        chipGroup.addView(chip)
    }

}

class TagChipBuilder(private val context: Context) {

    fun build(tag: Tag): Chip {
        val chip = Chip(ContextThemeWrapper(context.applicationContext, R.style.AppBaseChip))
        chip.text = tag.title
        chip.isClickable = true
        chip.isCloseIconVisible = true
        return chip
    }
}