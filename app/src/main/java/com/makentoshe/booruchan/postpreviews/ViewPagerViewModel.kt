package com.makentoshe.booruchan.postpreviews

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.postpreviews.model.ViewPagerController
import com.makentoshe.booruchan.postpreviews.model.ViewPagerRxController
import com.makentoshe.viewmodel.ViewModel

class ViewPagerViewModel : ViewModel(), ViewPagerController {

    private lateinit var viewPagerRxController: ViewPagerRxController
    private var currentPage: Int = 0

    override fun nextPage() = viewPagerRxController.action(++currentPage)

    override fun prevPage() = viewPagerRxController.action(--currentPage)

    override fun gotoPage(page: Int) {
        currentPage = page
        viewPagerRxController.action(page)
    }

    override fun onPageChangedListener(action: (Int) -> Unit) {
        viewPagerRxController.subscribe(action)
    }

    override fun onCreateView(owner: Fragment) {
        viewPagerRxController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        viewPagerRxController.clear()
    }

    class Factory : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = ViewPagerViewModel()
            viewModel.viewPagerRxController = ViewPagerRxController()
            return viewModel as T
        }
    }
}