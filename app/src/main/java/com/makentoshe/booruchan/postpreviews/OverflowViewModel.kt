package com.makentoshe.booruchan.postpreviews

import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.postpreviews.model.OverflowController
import com.makentoshe.booruchan.postpreviews.model.OverflowRxController
import com.makentoshe.viewmodel.ViewModel

class OverflowViewModel private constructor() : ViewModel(), OverflowController {
    private lateinit var overflowRxController: OverflowRxController

    override fun toCross() {
        overflowRxController.action(OverflowRxController.OverflowState.Cross)
    }

    override fun toMagnify() {
        overflowRxController.action(OverflowRxController.OverflowState.Magnify)
    }

    override fun clickOverflowIcon() {
        if (overflowRxController.state != null) {
            when (overflowRxController.state) {
                is OverflowRxController.OverflowState.Magnify ->
                    overflowRxController.action(OverflowRxController.OverflowState.Cross)
                is OverflowRxController.OverflowState.Cross ->
                    overflowRxController.action(OverflowRxController.OverflowState.Magnify)
                else -> Unit
            }
        } else overflowRxController.action(OverflowRxController.OverflowState.Cross)
    }

    override fun onOverflowStateChangedListener(action: OverflowRxController.OverflowListener.() -> Unit) {
        overflowRxController.subscribe(action)
    }

    override fun onCreateView(owner: androidx.fragment.app.Fragment) {
        overflowRxController.update()
    }

    override fun onCleared() {
        super.onCleared()
        overflowRxController.clear()
    }

    class Factory : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = OverflowViewModel()
            viewModel.overflowRxController = OverflowRxController(viewModel)
            return viewModel as T
        }
    }
}