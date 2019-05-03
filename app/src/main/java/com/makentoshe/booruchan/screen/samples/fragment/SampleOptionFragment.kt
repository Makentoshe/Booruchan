package com.makentoshe.booruchan.screen.samples.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.fragment.app.DialogFragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.samples.model.ExternalStorageDownloader
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

class SampleOptionFragment : DialogFragment() {

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private val disposables by inject<CompositeDisposable>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).setAdapter(buildAdapter(), ::onDialogItemClick).create()
    }

    private fun onDialogItemClick(dialog: DialogInterface, position: Int) {
        when (position) {
            0 -> onDownloadItemClick()
        }
    }

    private fun onDownloadItemClick() {
        val rxPermissions = RxPermissions(requireActivity())
        ExternalStorageDownloader(post, booru, rxPermissions).start(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    private fun buildAdapter(): ListAdapter {
        return ArrayAdapter.createFromResource(
            requireContext(), R.array.sample_options, android.R.layout.simple_list_item_1
        )
    }

    companion object {
        private const val POST = "Post"
        private const val BOORU = "Booru"
        fun create(booru: Booru, post: Post) = SampleOptionFragment().apply {
            this.post = post
            this.booru = booru
        }
    }
}

