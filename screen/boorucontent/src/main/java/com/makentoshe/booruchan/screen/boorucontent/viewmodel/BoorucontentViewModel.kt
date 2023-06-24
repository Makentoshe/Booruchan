package com.makentoshe.booruchan.screen.boorucontent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.makentoshe.booruchan.feature.context.BooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.usecase.GetBooruContextUseCase
import com.makentoshe.booruchan.feature.search.BooruSearch
import com.makentoshe.booruchan.feature.search.SearchTag
import com.makentoshe.booruchan.library.feature.CoroutineDelegate
import com.makentoshe.booruchan.library.feature.DefaultCoroutineDelegate
import com.makentoshe.booruchan.library.feature.DefaultEventDelegate
import com.makentoshe.booruchan.library.feature.DefaultNavigationDelegate
import com.makentoshe.booruchan.library.feature.DefaultStateDelegate
import com.makentoshe.booruchan.library.feature.EventDelegate
import com.makentoshe.booruchan.library.feature.NavigationDelegate
import com.makentoshe.booruchan.library.feature.StateDelegate
import com.makentoshe.booruchan.library.logging.internalLogInfo
import com.makentoshe.booruchan.library.logging.internalLogWarn
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.model.BooruPostPagingSourceFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoorucontentViewModel @Inject constructor(
    private val booruPostPagingSourceFactory: BooruPostPagingSourceFactory,
    private val getBooruContext: GetBooruContextUseCase,
) : ViewModel(), CoroutineDelegate by DefaultCoroutineDelegate(),
    EventDelegate<BoorucontentScreenEvent> by DefaultEventDelegate(),
    NavigationDelegate<BoorucontentDestination> by DefaultNavigationDelegate(),
    StateDelegate<BoorucontentScreenState> by DefaultStateDelegate(BoorucontentScreenState.InitialState) {

    // We're going to store BooruContext because there isn't any way BooruContext should be changed during this screen
    private val booruContextStateFlow = MutableStateFlow(BooruContext.EmptyContext)

    fun handleEvent(event: BoorucontentScreenEvent) = when (event) {
        is BoorucontentScreenEvent.Initialize -> initializeEvent(event)
        is BoorucontentScreenEvent.NavigationBack -> navigationBackEvent()
        is BoorucontentScreenEvent.Search -> searchEvent(event)
    }

    private fun initializeEvent(event: BoorucontentScreenEvent.Initialize) {
        internalLogInfo("initialize event invoked: $event")

        // Skip initialization if it was already finished
        // This may happen after lock-unlock phone screen
        if (booruContextStateFlow.value != BooruContext.EmptyContext) return

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler { throwable ->
            internalLogWarn(throwable.toString())
            updateState { copy(toolbarState = BoorucontentToolbarState.Error(throwable.toString())) }
        }) {
            getBooruContext(event.booruContextUrl).collectLatest(::onGetBooruContext)
        }
    }

    private fun onGetBooruContext(booruContext: BooruContext) {
        internalLogInfo("On get booru context success: $booruContext")

        // store boorucontext in viewmodel
        viewModelScope.launch { booruContextStateFlow.emit(booruContext) }

        // show booru title
        updateState { copy(
            toolbarState = BoorucontentToolbarState.Content(booruContext.title),
            bottomSheetState = BoorucontentBottomSheetState(
                queryHint = booruContext.settings.searchSettings.hint,
            )
        ) }

        // prepare pager for displaying booru posts
        val pagerFlow = Pager(PagingConfig(pageSize = booruContext.settings.searchSettings.requestedPostsPerPageCount)) {
            booruPostPagingSourceFactory.build(booruContext, BooruSearch(emptySet()))
        }.flow.cachedIn(viewModelScope)

        updateState { copy(pagerFlow = pagerFlow) }
    }

    private fun searchEvent(event: BoorucontentScreenEvent.Search) {
        internalLogInfo("search event invoked: $event")

        // Get BooruContext locally or do nothing
        val booruContext = booruContextStateFlow.value
        if (booruContext == BooruContext.EmptyContext) return

        // Map search query to list of tags
        val searchTags = event.searchQuery.split(booruContext.settings.searchSettings.tagSeparator).map { tag ->
            SearchTag(string = tag)
        }

        val booruSearch = BooruSearch(tags = searchTags.toSet())

        // Create new pager for displaying booru posts
        val pagerFlow = Pager(PagingConfig(pageSize = booruContext.settings.searchSettings.requestedPostsPerPageCount)) {
            booruPostPagingSourceFactory.build(booruContext, booruSearch)
        }.flow.cachedIn(viewModelScope)

        updateState { copy(pagerFlow = pagerFlow) }
    }

    private fun navigationBackEvent() {
        internalLogInfo("navigation back event invoked")
        updateNavigation { BoorucontentDestination.BackDestination }
    }

}