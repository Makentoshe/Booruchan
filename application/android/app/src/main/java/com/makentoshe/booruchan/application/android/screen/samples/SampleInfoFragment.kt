package com.makentoshe.booruchan.application.android.screen.samples

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Post
import kotlinx.android.synthetic.main.layout_download.*

class SampleInfoFragment : CoreFragment() {

    companion object {
        fun build(booruContextClass: Class<BooruContext>, post: Post): SampleInfoFragment {
            val fragment = SampleInfoFragment()
            fragment.arguments.booruclass = booruContextClass
            fragment.arguments.post = post
            return fragment
        }
    }

    val arguments = Arguments(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onViewCreatedDownload()
    }

    private fun onViewCreatedDownload() {
        // TODO mb add permission rationale?
        val readWriteStoragePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val permissionCheckStatus = ContextCompat.checkSelfPermission(requireContext(), readWriteStoragePermission)
        if (permissionCheckStatus == PackageManager.PERMISSION_GRANTED) {
            layout_download_text.text = getString(R.string.layout_download_permission_granted)
        } else {
            layout_download_text.text = getString(R.string.layout_download_permission_requires)
        }
        layout_download_button.setOnClickListener {
            if (permissionCheckStatus != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(readWriteStoragePermission), 0)
            }

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissions.zip(grantResults.map { it == PackageManager.PERMISSION_GRANTED }.toTypedArray()).forEach {
            when (it.first) {
                Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                    if (it.second) layout_download_text.text = getString(R.string.layout_download_permission_granted)
                }
                else -> println("${it.first}: ${it.second}")
            }
        }
    }

    class Arguments(fragment: SampleInfoFragment) : FragmentArguments(fragment) {

        var post: Post
            get() = fragmentArguments.getSerializable(POST) as Post
            set(value) = fragmentArguments.putSerializable(POST, value)

        var booruclass: Class<BooruContext>
            get() = fragmentArguments.getSerializable(CLASS) as Class<BooruContext>
            set(value) = fragmentArguments.putSerializable(CLASS, value)

        companion object {
            private const val POST = "post"
            private const val CLASS = "class"
        }
    }
}