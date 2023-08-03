package com.makentoshe.booruchan.screen.source.viewmodel

import androidx.lifecycle.ViewModel
import com.makentoshe.booruchan.extension.base.Source
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
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SourceScreenViewModel @Inject constructor(
    private val pluginFactory: PluginFactory,
    private val findAllPlugins: GetAllPluginsUseCase,
) : ViewModel(), CoroutineDelegate by DefaultCoroutineDelegate(),
    StateDelegate<SourceScreenState> by DefaultStateDelegate(SourceScreenState.InitialState),
    EventDelegate<SourceScreenEvent> by DefaultEventDelegate(),
    NavigationDelegate<SourceScreenDestination> by DefaultNavigationDelegate() {

    /** Will be initialized right after constructor at [SourceScreenEvent.Initialize] invocation */
    private lateinit var source: Source

    override fun handleEvent(event: SourceScreenEvent) = when(event){
        is SourceScreenEvent.Initialize -> initialize(event)
    }

    private fun initialize(event: SourceScreenEvent.Initialize) {
        internalLogInfo("invoke initialize for Source(${event.sourceId})")

        val source = findAllPlugins().map(pluginFactory::buildSource).find { source -> source?.id == event.sourceId }
        if (source == null) { // TODO: handle this issue
            updateState { copy(contentState = ContentState.Failure("Source wasn't found")) }
        } else {
            this.source = source
        }

        updateState { copy(contentState = ContentState.Success(source.toString())) }
        // TODO: request initial source page items
    }

}
