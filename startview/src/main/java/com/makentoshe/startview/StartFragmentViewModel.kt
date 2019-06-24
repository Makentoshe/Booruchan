package com.makentoshe.startview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.api.repository.BooruRepository
import com.makentoshe.api.repository.Repository
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.settings.SettingsBuilder

/**
 * Viewmodel component for [StartFragment].
 * Contains a [StartFragmentNavigator] instance and [SettingsBuilder] instance
 */
class StartFragmentViewModel(
    val navigator: StartFragmentNavigator,
    val settingsBuilder: SettingsBuilder,
    val booruRepository: Repository<Any, List<Booru>>
) : ViewModel() {

    class Factory(
        private val navigator: StartFragmentNavigator?,
        private val settingsBuilder: SettingsBuilder?,
        private val booruRepository: Repository<Any, List<Booru>>?
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return StartFragmentViewModel(navigator!!, settingsBuilder!!, booruRepository!!) as T
        }
    }
}