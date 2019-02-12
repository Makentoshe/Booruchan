package com.makentoshe.booruchan.postpreviews

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.postpreviews.model.ClearIconController
import com.makentoshe.booruchan.postpreviews.model.ClearIconRxController
import com.makentoshe.viewmodel.ViewModel

/**
 * Wraps [ClearIconRxController].
 */
class ClearIconViewModel(
    private val clearIconRxController: ClearIconRxController
) : ViewModel(), ClearIconController {

    override fun clearIconClick() = clearIconRxController.action(Unit)

    override fun onClearIconClickListener(action: () -> Unit) {
        clearIconRxController.subscribe { action() }
    }

    override fun onCreateView(owner: Fragment) = clearIconRxController.clear()

    override fun onCleared() {
        super.onCleared()
        clearIconRxController.clear()
    }

    class Factory : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = ClearIconViewModel(ClearIconRxController())
            return viewModel as T
        }
    }
}