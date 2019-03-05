package com.makentoshe.booruchan.postsamples

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.UnitRxController
import com.makentoshe.booruchan.postsamples.animation.BarMoveDownAnimator
import com.makentoshe.booruchan.postsamples.animation.BarMoveUpAnimator
import com.makentoshe.booruchan.postsamples.animation.ViewPagerHidePaddingAnimator
import com.makentoshe.booruchan.postsamples.animation.ViewPagerShowPaddingAnimator
import com.makentoshe.booruchan.postsamples.model.*
import com.makentoshe.booruchan.postsamples.view.PostSamplesUi
import com.makentoshe.viewmodel.ViewModel
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import java.io.Serializable

class PostSamplesFragment : androidx.fragment.app.Fragment() {

    /* Performs file downloading into external storage */
    private lateinit var downloadFileViewModel: DownloadFileViewModel
    /* Performs any navigation */
    private lateinit var navigationController: NavigationController

    private lateinit var adapterBuilder: ViewPagerAdapterBuilder
    /* Performs permissions check and request */
    private lateinit var permissionChecker: PermissionCheckController

    private lateinit var notificationController: NotificationController

    private lateinit var fullScreenController: FullScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val booru = arguments!!.get(BOORU) as Booru
        val tags = arguments!!.get(TAGS) as Set<Tag>
        val position = arguments!!.getInt(POSITION)

        permissionChecker = PermissionChecker.Builder().build()
        setPermissionListener(requireActivity())

        notificationController = NotificationControllerImpl()

        var factory: ViewModelProvider.NewInstanceFactory =
            DownloadFileViewModel.Factory(booru, tags, permissionChecker, notificationController)
        downloadFileViewModel =
                ViewModelProviders.of(this, factory)[DownloadFileViewModel::class.java]

        val router = Booruchan.INSTANCE.router
        navigationController = NavigationControllerImpl(router, booru, position, tags)

        factory = FullScreenViewModel.Factory(false)
        fullScreenController = ViewModelProviders.of(this, factory)[FullScreenViewModel::class.java]

        adapterBuilder = PostSamplesVerticalViewPagerAdapterBuilder(booru, tags, position, fullScreenController)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        downloadFileViewModel.onCreateView(this)
        fullScreenController.onCreateView(this)
        return PostSamplesUi(adapterBuilder, downloadFileViewModel, navigationController)
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbar = view.find<View>(R.id.postsamples_toolbar)
        val bottombar = view.find<View>(R.id.postsamples_bottombar)
        val container = view.find<View>(R.id.postsamples_verticalpager)
        fullScreenController.subscribe {
            fullScreenController.perform(toolbar, container, bottombar)
        }
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

interface FullScreenController : Serializable {
    fun perform()
    fun subscribe(action: () -> Unit)
}

class FullScreenViewModel(initialValue: Boolean) : ViewModel(), FullScreenController {

    private lateinit var controller: UnitRxController

    private var isFullScreen = initialValue

    override fun perform() = controller.action(Unit)

    override fun subscribe(action: () -> Unit) {
        controller.subscribe { Handler(Looper.getMainLooper()).post { action() } }
    }

    override fun onCreateView(owner: Fragment) {
        if (isFullScreen) perform()
        controller.clear()
    }

    internal fun perform(toolbar: View, content: View, bottombar: View) {
        if (isFullScreen) {
            isFullScreen = false
            undoFullScreen(toolbar, content, bottombar)
        } else {
            isFullScreen = true
            makeFullScreen(toolbar, content, bottombar)
        }
    }

    private fun makeFullScreen(toolbar: View, content: View, bottombar: View) {
        BarMoveUpAnimator(toolbar, 100).animate()
        BarMoveDownAnimator(bottombar, 100).animate()
        ViewPagerHidePaddingAnimator(content, content.context.dip(56), 100).animate()
    }

    private fun undoFullScreen(toolbar: View, content: View, bottombar: View) {
        BarMoveDownAnimator(toolbar, 100).animate()
        BarMoveUpAnimator(bottombar, 100).animate()
        ViewPagerShowPaddingAnimator(content, content.context.dip(56), 150).animate()
    }

    override fun onCleared() {
        super.onCleared()
        controller.clear()
    }

    class Factory(private val initialState: Boolean) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = FullScreenViewModel(initialState)
            viewModel.controller = UnitRxController()
            return viewModel as T
        }
    }
}