package com.makentoshe.imageview.download

import android.app.Dialog
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.DialogFragment
import com.makentoshe.api.repository.RepositoryBuilder
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.imageview.AndroidImageDecoder
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import java.io.File

/** Dialog for a sample item. Contains a list of actions that can be performed with this item */
class SampleItemDialogMenuFragment : DialogFragment() {

    /** Container for [io.reactivex.disposables.Disposable] objects will be released on destroy lifecycle event */
    private val disposables = CompositeDisposable()

    /** Current [Booru] API instance used for requests */
    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable("Booru", value)
        get() = arguments!!.get("Booru") as Booru

    /** Current [Post] instance used for requests */
    private var post: Post
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable("Post", value)
        get() = arguments!!.get("Post") as Post

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val repositorybuilder = RepositoryBuilder(booru)
        val permissionexecutor =
            CustomPermissionExecutor(RxPermissions(requireActivity()), disposables)
        val notificationExecutor =
            CustomNotificationExecutor(requireContext(), AndroidImageDecoder())

        val downloadexecutor = CustomDownloadExecutor(
            permissionexecutor, notificationExecutor, getDownloadFolder(), repositorybuilder, disposables
        )

        val presenter = SampleItemDialogMenuPresenter(post, downloadexecutor)
        return presenter.bindDialog(requireContext())
    }

    /** Returns a folder to files */
    private fun getDownloadFolder() :File {
        val appname = requireContext().applicationInfo.loadLabel(requireContext().packageManager)
        val downloadfolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        return File(downloadfolder, "$appname/${booru.title}")
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        fun build(booru: Booru, post: Post): SampleItemDialogMenuFragment {
            val fragment = SampleItemDialogMenuFragment()
            fragment.booru = booru
            fragment.post = post
            return fragment
        }
    }
}
