package com.makentoshe.booruchan.screen.source.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.makentoshe.booruchan.feature.PluginFactory
import com.makentoshe.booruchan.library.feature.CoroutineDelegate
import com.makentoshe.booruchan.library.feature.DefaultCoroutineDelegate
import com.makentoshe.booruchan.library.feature.DefaultEventDelegate
import com.makentoshe.booruchan.library.feature.DefaultNavigationDelegate
import com.makentoshe.booruchan.library.feature.DefaultStateDelegate
import com.makentoshe.booruchan.library.feature.EventDelegate
import com.makentoshe.booruchan.library.feature.NavigationDelegate
import com.makentoshe.booruchan.library.feature.StateDelegate
import com.makentoshe.booruchan.library.logging.internalLogInfo
import com.makentoshe.booruchan.library.plugin.GetAllPluginsUseCase
import com.makentoshe.booruchan.screen.source.paging.PagingSourceFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SourceScreenViewModel @Inject constructor(
    private val pluginFactory: PluginFactory,
    private val findAllPlugins: GetAllPluginsUseCase,
    private val pagingSourceFactory: PagingSourceFactory,
) : ViewModel(), CoroutineDelegate by DefaultCoroutineDelegate(),
    StateDelegate<SourceScreenState> by DefaultStateDelegate(SourceScreenState.InitialState),
    EventDelegate<SourceScreenEvent> by DefaultEventDelegate(),
    NavigationDelegate<SourceScreenDestination> by DefaultNavigationDelegate() {

    override fun handleEvent(event: SourceScreenEvent) = when (event) {
        is SourceScreenEvent.Initialize -> initialize(event)
    }

    private fun initialize(event: SourceScreenEvent.Initialize) {
        internalLogInfo("invoke initialize for Source(${event.sourceId})")

        // Skip already loaded content
        if (state.contentState !is ContentState.Loading) {
            return internalLogInfo("skip initialize for Source(${event.sourceId})")
        }

        // find source from plugin by source id or show failure state
        val source = findAllPlugins().map(pluginFactory::buildSource).find { source -> source?.id == event.sourceId }
            ?: return updateState { copy(contentState = pluginSourceNullContentState()) }

        // get fetch posts factory or show failure state
        val fetchPostsFactory = source.fetchPostsFactory
            ?: return updateState { copy(contentState = pluginFetchPostFactoryNullContentState()) }

        val pagingSource = pagingSourceFactory.buildPost(fetchPostsFactory)

        val pagerFlow = Pager(PagingConfig(pageSize = fetchPostsFactory.requestedPostsPerPageCount)) {
            pagingSource
        }.flow.cachedIn(viewModelScope)

        updateState {
            copy(contentState = ContentState.Success(pagerFlow = pagerFlow))
        }
    }

    private fun pluginSourceNullContentState() : ContentState.Failure {
        return ContentState.Failure("Could not determine Source for this plugin")
    }

    private fun pluginFetchPostFactoryNullContentState() : ContentState.Failure {
        return ContentState.Failure("Could not determine FetchPostFactory for this Source")
    }
}
