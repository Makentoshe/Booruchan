package com.makentoshe.booruchan.screen.search

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.screen.arguments
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

class SearchDialogFragment : DialogFragment() {

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = SearchDialogUi().createView(AnkoContext.create(requireContext(), this))
        return AlertDialog.Builder(requireContext()).setTitle("Start a new search").setView(view).create()
            .also(::setParams)
            .also { onViewCreated(view, savedInstanceState) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        SearchDialogEditTextInflater(tags, this).inflate(view)
    }

    private fun setParams(dialog: Dialog) = Unit

    companion object {
        const val SEARCH_CODE = 1
        private const val TAGS = "Tags"
        fun create(tags: Set<Tag>) = SearchDialogFragment().apply {
            this.tags = tags
        }
    }
}

