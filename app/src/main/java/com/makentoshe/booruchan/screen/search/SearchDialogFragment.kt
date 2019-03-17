package com.makentoshe.booruchan.screen.search

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.fragment.app.DialogFragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.repository.DelayAutocompleteRepository
import com.makentoshe.booruchan.screen.arguments
import com.makentoshe.booruchan.view.DelayAutocompleteEditText
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import java.io.Serializable

class SearchDialogFragment : DialogFragment() {

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    //need global instance for gettings tags
    private val searchDialogInflater by lazy {
        SearchDialogEditTextInflater(tags, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //after recreation the set of tags will be saved
        //in the bundle, so just restore them into global var
        //note: tags set from the bundle also contains the original set
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val editText = view.find<DelayAutocompleteEditText>(R.id.searchDialog_delayAutocompleteEditText)
        val repository = DelayAutocompleteRepository(booru)
        val adapter = DelayAutocompleteAdapter(repository)
        editText.setAdapter(adapter)
        editText.progressBar = view.find(R.id.search_progress)
        searchDialogInflater.accept(view)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(TAGS, searchDialogInflater.tags)
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