package com.makentoshe.screen.boorulist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makentoshe.booruchan.extension.base.Source
import com.makentoshe.booruchan.feature.PluginFactory
import com.makentoshe.booruchan.feature.healthcheck.HealthcheckUseCase
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
import com.makentoshe.booruchan.library.plugin.GetAllPluginsUseCase
import com.makentoshe.screen.boorulist.entity.SourceHealthUi
import com.makentoshe.screen.boorulist.mapper.Source2SourceUiStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val pluginFactory: PluginFactory,

    private val findAllPlugins: GetAllPluginsUseCase,
    private val healthcheckUseCase: HealthcheckUseCase,

    private val source2SourceUiStateMapper: Source2SourceUiStateMapper,
) : ViewModel(), CoroutineDelegate by DefaultCoroutineDelegate(),
    StateDelegate<HomeScreenState> by DefaultStateDelegate(HomeScreenState.InitialState),
    EventDelegate<HomeScreenEvent> by DefaultEventDelegate(),
    NavigationDelegate<HomeScreenDestination> by DefaultNavigationDelegate() {

    init {
        internalLogInfo("OnViewModelConstruct")

        refreshPlugins()
    }

    private fun onHealthCheckSource(source: Source) {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            onHealthCheckFailure(source, throwable)
        }) {
            val factory = source.healthCheckFactory
                ?: throw IllegalStateException("health check factory is null")

            onHealthCheckSuccess(source, healthcheckUseCase(factory.buildRequest()))
        }
    }

    private fun onHealthCheckFailure(source: Source, throwable: Throwable) {
        internalLogWarn("Healthcheck(${source.id}): $throwable")
        updateSourceUiStateHealthCheck(source, SourceHealthUi.Unavailable)
    }

    private fun onHealthCheckSuccess(source: Source, isAvailabile: Boolean) {
        internalLogInfo("Healthcheck(${source.id}): isAvailable=$isAvailabile")
        if (isAvailabile) {
            updateSourceUiStateHealthCheck(source, SourceHealthUi.Available)
        } else {
            updateSourceUiStateHealthCheck(source, SourceHealthUi.Unavailable)
        }
    }

    private fun updateSourceUiStateHealthCheck(source: Source, availability: SourceHealthUi) = updateState {
        // Check current state is Content
        // We assume that health state updating request will be called
        // right after content will be displayed, so in this case exception will never be thrown
        val content = (pluginContent as? HomeScreenPluginContent.Content)
            ?: throw IllegalStateException(state.toString())

        // O(n^2) but we don't care. This list just cant contain more that 100-200 items
        val sources = content.sources.map { sourceUiState ->
            if (sourceUiState.id != source.id) return@map sourceUiState else {
                return@map sourceUiState.copy(health = availability)
            }
        }

        copy(pluginContent = HomeScreenPluginContent.Content(sources = sources, refreshing = false))
    }

    override fun handleEvent(event: HomeScreenEvent) = when (event) {
        is HomeScreenEvent.NavigationSource -> navigateSource(event)
        HomeScreenEvent.RefreshPlugins -> refreshPlugins()
    }

    private fun refreshPlugins() {
        internalLogInfo("refresh plugins invoked")

        viewModelScope.launch(Dispatchers.IO) {
            val sources = findAllPlugins().mapNotNull(pluginFactory::buildSource).onEach(::onHealthCheckSource)
            val sourceUiList = sources.map(source2SourceUiStateMapper::map)
            updateState {
                copy(pluginContent = HomeScreenPluginContent.Content(sources = sourceUiList, refreshing = false))
            }
        }
    }

    private fun navigateSource(event: HomeScreenEvent.NavigationSource) {
        updateNavigation { HomeScreenDestination.SourceDestination(sourceId = event.sourceId) }
    }
}