package com.makentoshe.screen.boorulist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makentoshe.booruchan.extension.base.Source
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
import com.makentoshe.screen.boorulist.mapper.Source2SourceUiStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PluginFactory {


}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val findAllPlugins: GetAllPluginsUseCase,

    private val source2SourceUiStateMapper: Source2SourceUiStateMapper,
) : ViewModel(), CoroutineDelegate by DefaultCoroutineDelegate(),
    StateDelegate<HomeScreenState> by DefaultStateDelegate(HomeScreenState.InitialState),
    EventDelegate<HomeScreenEvent> by DefaultEventDelegate(),
    NavigationDelegate<HomeScreenDestination> by DefaultNavigationDelegate() {

    init {
        internalLogInfo("OnViewModelConstruct")

        viewModelScope.launch(Dispatchers.IO) {
            val sources = findAllPlugins().mapNotNull { plugin ->
                plugin.sourceClass.getDeclaredConstructor().newInstance() as? Source
            }

            val sourceUiList = sources.map { source2SourceUiStateMapper.map(it) }

            updateState {
                copy(pluginContent = HomeScreenPluginContent.Content(sources = sourceUiList))
            }
        }
    }

    fun handleEvent(event: HomeScreenEvent) = when (event) {
        else -> {}
    }

}