package com.makentoshe.settingsview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.settings.common.SettingsBuilder

/**
 * [ViewModel] component stores a [SettingsBuilder] instance to avoid serialization problems.
 */
class SettingsFragmentViewModel(val settingsBuilder: SettingsBuilder) : ViewModel() {

    /**
     * Factory class used for creating a [SettingsFragmentViewModel] instance.
     *
     * @param settingsBuilder may be null, but if [SettingsFragmentViewModel] instance was not initialized yet a
     * [KotlinNullPointerException] will be thrown.
     */
    class Factory(private val settingsBuilder: SettingsBuilder?) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SettingsFragmentViewModel(settingsBuilder!!) as T
        }
    }
}
