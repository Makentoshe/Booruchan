package com.makentoshe.booruchan.postsamples

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.UnitRxController
import com.makentoshe.booruchan.postsamples.animation.BarMoveDownAnimator
import com.makentoshe.booruchan.postsamples.animation.BarMoveUpAnimator
import com.makentoshe.booruchan.postsamples.animation.ViewPagerHidePaddingAnimator
import com.makentoshe.booruchan.postsamples.animation.ViewPagerShowPaddingAnimator
import com.makentoshe.viewmodel.ViewModel
import org.jetbrains.anko.dip
import java.io.Serializable

interface FullScreenController : Serializable {
    fun perform()
    fun subscribe(action: () -> Unit)
}

class FullScreenViewModel(initialValue: Boolean) : ViewModel(),
    FullScreenController, Serializable {

    @Transient
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
        ViewPagerHidePaddingAnimator(
            content,
            content.context.dip(56),
            100
        ).animate()
    }

    private fun undoFullScreen(toolbar: View, content: View, bottombar: View) {
        BarMoveDownAnimator(toolbar, 100).animate()
        BarMoveUpAnimator(bottombar, 100).animate()
        ViewPagerShowPaddingAnimator(
            content,
            content.context.dip(56),
            150
        ).animate()
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