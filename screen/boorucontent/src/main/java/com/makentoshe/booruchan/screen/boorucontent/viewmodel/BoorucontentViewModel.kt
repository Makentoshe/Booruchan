package com.makentoshe.booruchan.screen.boorucontent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.makentoshe.booruchan.extension.BooruSource
import com.makentoshe.booruchan.extension.factory.AutoCompleteTagFactory
import com.makentoshe.booruchan.extension.factory.BooruPostSearchFactory
import com.makentoshe.booruchan.extension.usecase.GetBooruSourceUseCase
import com.makentoshe.booruchan.feature.autocomplete.FetchAutoCompleteTagsUseCase
import com.makentoshe.booruchan.feature.post.BooruPost
import com.makentoshe.booruchan.feature.search.BooruSearch
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
import com.makentoshe.booruchan.screen.boorucontent.domain.BooruPreviewPostUi
import com.makentoshe.booruchan.screen.boorucontent.mapper.AutoCompleteTag2AutoCompleteTagUiMapper
import com.makentoshe.booruchan.screen.boorucontent.mapper.AutoCompleteTagUi2SearchTagUiMapper
import com.makentoshe.booruchan.screen.boorucontent.mapper.SearchTagUi2SearchTagMapper
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.model.BooruPostPagingSourceFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoorucontentViewModel @Inject constructor(
    private val getBooruSource: GetBooruSourceUseCase,
    private val fetchAutoCompleteTags: FetchAutoCompleteTagsUseCase,
    private val booruPostPagingSourceFactory: BooruPostPagingSourceFactory,
    private val autoCompleteTagUiMapper: AutoCompleteTag2AutoCompleteTagUiMapper,
    private val searchTagUiMapper: AutoCompleteTagUi2SearchTagUiMapper,
    private val searchTagUi2SearchTagMapper: SearchTagUi2SearchTagMapper,
) : ViewModel(), CoroutineDelegate by DefaultCoroutineDelegate(),
    EventDelegate<BoorucontentScreenEvent> by DefaultEventDelegate(),
    NavigationDelegate<BoorucontentDestination> by DefaultNavigationDelegate(),
    StateDelegate<BoorucontentScreenState> by DefaultStateDelegate(BoorucontentScreenState.InitialState) {

    private val booruSourceStateFlow = MutableStateFlow<BooruSource?>(null)

    fun handleEvent(event: BoorucontentScreenEvent) = when (event) {
        is BoorucontentScreenEvent.Initialize -> initializeEvent(event)
        is BoorucontentScreenEvent.NavigationBack -> navigationBackEvent()
        is BoorucontentScreenEvent.Search -> searchEvent(event)
        is BoorucontentScreenEvent.AutoCompleteTag -> autocompleteEvent(event)
        is BoorucontentScreenEvent.AddSearchTag -> addSearchTagEvent(event)
        is BoorucontentScreenEvent.RemoveSearchTag -> removeSearchTagEvent(event)
    }

    private fun initializeEvent(event: BoorucontentScreenEvent.Initialize) {
        internalLogInfo("initialize event invoked: $event")

        // Skip initialization if it was already finished
        // This may happen after lock-unlock phone screen
        if (booruSourceStateFlow.value != null) return

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler { throwable ->
            internalLogWarn(throwable.toString())
            updateState { copy(toolbarState = BoorucontentToolbarState.Error(throwable.toString())) }
        }) {
            getBooruSource(event.booruSourceId).collectLatest(::onGetBooruSource)
        }
    }

    private fun onGetBooruSource(booruSource: BooruSource) {
        internalLogInfo("On get booru context success: $booruSource")

        // store boorucontext in viewmodel
        viewModelScope.launch { booruSourceStateFlow.emit(booruSource) }

        // show booru title
        updateState {
            copy(
                toolbarState = BoorucontentToolbarState.Content(booruSource.context.title),
                bottomSheetState = BoorucontentBottomSheetState()
            )
        }

        // prepare pager for displaying booru posts
        val postSearchFactory = booruSource.postSearchFactory
        val pagerFlow = postSearchFactory?.let(::buildContentPagingFlow)?: buildErrorPagingFlow()
        updateState { copy(pagerFlow = pagerFlow) }
    }

    private fun searchEvent(event: BoorucontentScreenEvent.Search) {
        internalLogInfo("search event invoked: $event")

        val booruSource = booruSourceStateFlow.value ?: return
        val postSearchFactory = booruSource.postSearchFactory

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler { throwable ->
            internalLogWarn(throwable.toString())
        }) {
            val pagerFlow = postSearchFactory?.let(::buildContentPagingFlow)?: buildErrorPagingFlow()
            updateState { copy(pagerFlow = pagerFlow) }
        }
    }

    private fun autocompleteEvent(event: BoorucontentScreenEvent.AutoCompleteTag) {
        internalLogInfo("autocomplete event invoked: $event")

        val booruSource = booruSourceStateFlow.value ?: return
        val autoCompleteTagFactory = booruSource.autoCompleteTagFactory ?: return

        // Context for defining a single coroutine for autocompleting
        // We also able to cancel it if we need to autocomplete a new tag
        val coroutineContext = Dispatchers.IO + CoroutineName("Autocomplete") + coroutineExceptionHandler {
            updateState { copy(bottomSheetState = bottomSheetState.copy(queryAutocomplete = emptyList())) }
        }
        // cancel previous autocompletion if possible
        coroutineContext.cancelChildren()
        // launch autocompletion
        viewModelScope.launch(coroutineContext) {
            val fetchTagsRequest = AutoCompleteTagFactory.FetchTagsRequest(event.autocompleteQuery)
            val autoCompleteTags = fetchAutoCompleteTags(fetchTagsRequest, autoCompleteTagFactory)

            updateState {
                copy(
                    bottomSheetState = bottomSheetState.copy(
                        queryAutocomplete = autoCompleteTags.map(autoCompleteTagUiMapper::map)
                    )
                )
            }
        }
    }

    private fun removeSearchTagEvent(event: BoorucontentScreenEvent.RemoveSearchTag) {
        internalLogInfo("remove search tag event invoked: $event")

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler { throwable ->
            internalLogWarn(throwable.toString())
        }) {
            updateState {
                copy(
                    bottomSheetState = bottomSheetState.copy(
                        queryAutocomplete = emptyList(),
                        queryTags = bottomSheetState.queryTags.minus(event.tag)
                    )
                )
            }
        }
    }

    private fun addSearchTagEvent(event: BoorucontentScreenEvent.AddSearchTag) {
        internalLogInfo("add search tag event invoked: $event")

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler { throwable ->
            internalLogWarn(throwable.toString())
        }) {
            val autoCompleteTagUi = state.bottomSheetState.queryAutocomplete[event.index]
            val searchTagUi = searchTagUiMapper.map(autoCompleteTagUi)

            updateState {
                copy(
                    bottomSheetState = bottomSheetState.copy(
                        queryAutocomplete = emptyList(),
                        queryTags = bottomSheetState.queryTags.plus(searchTagUi),
                    ),
                )
            }
        }
    }

    private fun navigationBackEvent() {
        internalLogInfo("navigation back event invoked")
        updateNavigation { BoorucontentDestination.BackDestination }
    }



    private fun buildErrorPagingFlow(): Flow<PagingData<BooruPreviewPostUi>> {
        // Create new pager for displaying booru posts
        return Pager(PagingConfig(pageSize = 1)) {
            booruPostPagingSourceFactory.error(Exception("SAS"))
        }.flow.cachedIn(viewModelScope)
    }

    private fun buildContentPagingFlow(postSearchFactory: BooruPostSearchFactory): Flow<PagingData<BooruPreviewPostUi>> {
        // Get search tags from current screen state
        val searchTags = state.bottomSheetState.queryTags.map(searchTagUi2SearchTagMapper::map)
        val booruSearch = BooruSearch(tags = searchTags.toSet())

        // Create new pager for displaying booru posts
        return Pager(PagingConfig(pageSize = postSearchFactory.requestedPostsPerPageCount)) {
            booruPostPagingSourceFactory.build(postSearchFactory, booruSearch)
        }.flow.cachedIn(viewModelScope)
    }

}