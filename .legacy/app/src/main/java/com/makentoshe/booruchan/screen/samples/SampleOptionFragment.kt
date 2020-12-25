package com.makentoshe.booruchan.screen.samples

import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.RequestCode
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.permission.PermissionController
import com.makentoshe.booruchan.screen.samples.model.DownloadIntoInternalStorageProcess

class SampleOptionFragment : DialogFragment() {

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private val permissionController by lazy {
        PermissionController(lifecycle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).setAdapter(buildAdapter()) { dialog, which ->
            when (which) {
                0 -> DownloadIntoInternalStorageProcess(post, booru).start(requireContext(), permissionController)
            }
        }.create()
    }

    override fun onStart() {
        super.onStart()
        permissionController.handlePermissionRequest {
            val status = ContextCompat.checkSelfPermission(requireContext(), it)
            if (status == PackageManager.PERMISSION_GRANTED) {
                permissionController.sendPermissionResult(true)
            } else {
                requestPermissions(arrayOf(it), RequestCode.permission)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //the permission request will be always for one permission at the time.
        permissionController.sendPermissionResult(grantResults[0] == PackageManager.PERMISSION_GRANTED)
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