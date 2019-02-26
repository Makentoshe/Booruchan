package com.makentoshe.booruchan.postsamples

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.AppActivity
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.postsamples.model.NavigationController
import com.makentoshe.booruchan.postsamples.model.PermissionCheckController
import com.makentoshe.booruchan.postsamples.model.PermissionChecker
import com.makentoshe.booruchan.postsamples.model.ViewPagerAdapterBuilder
import com.makentoshe.booruchan.postsamples.view.PostSamplesUi
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

class PostSamplesFragment : androidx.fragment.app.Fragment() {

    /* Performs file downloading into external storage */
    private lateinit var downloadFileViewModel: DownloadFileViewModel
    /* Performs any navigation */
    private lateinit var navigationController: NavigationController

    private lateinit var adapterBuilder: ViewPagerAdapterBuilder
    /* Performs permissions check and request */
    private lateinit var permissionChecker: PermissionCheckController

    override fun onCreate(savedInstanceState: Bundle?) {
        val booru = arguments!!.get(BOORU) as Booru
        val tags = arguments!!.get(TAGS) as Set<Tag>
        val position = arguments!!.getInt(POSITION)
        adapterBuilder = PostSamplesVerticalViewPagerAdapterBuilder(booru, tags, position)

        permissionChecker = PermissionChecker.Builder().build()
        setPermissionListener(requireActivity())

        val snackbarNotificationController = (requireActivity() as AppActivity).snackbarNotificationController
        val factory = DownloadFileViewModel.Factory(booru, tags, permissionChecker, snackbarNotificationController)
        downloadFileViewModel = ViewModelProviders.of(this, factory)[DownloadFileViewModel::class.java]

        val router = Booruchan.INSTANCE.router
        navigationController = NavigationControllerImpl(router, booru, position, tags)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        downloadFileViewModel.onCreateView(this)
        return PostSamplesUi(adapterBuilder, downloadFileViewModel, navigationController).createView(
            AnkoContext.create(requireContext(), this)
        )
    }

    private fun setPermissionListener(activity: Activity) {
        permissionChecker.handlePermissionRequest {
            val status = ContextCompat.checkSelfPermission(activity, it)
            if (status == PackageManager.PERMISSION_GRANTED) {
                permissionChecker.sendPermissionResult(true)
            } else {
                requestPermissions(arrayOf(it), 1)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //the permission request will be always for one permission at the time.
        permissionChecker.sendPermissionResult(grantResults[0] == PackageManager.PERMISSION_GRANTED)
    }

    override fun onDestroy() {
        super.onDestroy()
        permissionChecker.clear()
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        private const val POSITION = "Position"

        fun create(booru: Booru, tags: Set<Tag>, position: Int): androidx.fragment.app.Fragment {
            return PostSamplesFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BOORU, booru)
                    putSerializable(TAGS, tags as Serializable)
                    putInt(POSITION, position)
                }
            }
        }
    }
}