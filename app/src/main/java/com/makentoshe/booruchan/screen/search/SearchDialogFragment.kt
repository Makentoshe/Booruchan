package com.makentoshe.booruchan.screen.search

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.makentoshe.booruchan.screen.arguments
import com.makentoshe.booruchan.screen.posts.model.TagsController
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext

class SearchDialogFragment : DialogFragment() {

    private val disposables by lazy {
        CompositeDisposable()
    }

    private var tagsController: TagsController
        get() = arguments!!.get(TCTRL) as TagsController
        set(value) = arguments().putSerializable(TCTRL, value)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = SearchDialogUi().createView(AnkoContext.create(requireContext(), this))
        return AlertDialog.Builder(requireContext()).setTitle("Start a new search").setView(view).create()
            .also(::setParams)
            .also { onViewCreated(view, savedInstanceState) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        SearchDialogEditTextInflater(disposables, tagsController, this).inflate(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    private fun setParams(dialog: Dialog) = Unit

    companion object {
        private const val TCTRL = "TagsController"
        private const val SCTRL = "SearchController"
        fun create(tagsController: TagsController) = SearchDialogFragment().apply {
            this.tagsController = tagsController
        }
    }
}