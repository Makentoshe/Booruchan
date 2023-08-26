package com.makentoshe.booruchan.feature

import com.makentoshe.booruchan.extension.base.Source
import com.makentoshe.booruchan.library.plugin.Plugin
import javax.inject.Inject

class PluginFactory @Inject constructor() {

    fun buildSource(plugin: Plugin): Source? {
        val instance = plugin.sourceClass.getDeclaredConstructor().newInstance()
        return SourceWrapper(instance as? Source ?: return null)
    }

}
