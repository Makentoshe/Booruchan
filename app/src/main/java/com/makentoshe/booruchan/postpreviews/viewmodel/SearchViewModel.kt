package com.makentoshe.booruchan.postpreviews.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.postpreviews.model.SearchController
import com.makentoshe.booruchan.postpreviews.model.SearchRxController
import com.makentoshe.viewmodel.ViewModel

class SearchViewModel private constructor() : ViewModel(), SearchController {
    /* Performs search controlling */
    private lateinit var searchRxController: SearchRxController
    /* Contains a tag set with current search */
    private lateinit var currentSearchTagSet: Set<Tag>

    /** Starts a new search. */
    override fun startSearch(tags: Set<Tag>) = searchRxController.newSearch(tags)

    /** Subscribe on a new search. */
    override fun onSearchStartedListener(action: (Set<Tag>) -> Unit) {
        searchRxController.subscribe {
            currentSearchTagSet = it
            action(it)
        }
    }

    override fun onCreateView(owner: Fragment) {
        searchRxController.clear()
        startSearch(currentSearchTagSet)
    }

    override fun onCleared() {
        super.onCleared()
        searchRxController.clear()
    }

    class Factory(private val tags: Set<Tag>) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = SearchViewModel()
            viewModel.searchRxController = SearchRxController()
            viewModel.currentSearchTagSet = tags
            return viewModel as T
        }
    }
}