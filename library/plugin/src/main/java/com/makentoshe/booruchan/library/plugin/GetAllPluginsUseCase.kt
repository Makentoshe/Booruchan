package com.makentoshe.booruchan.library.plugin

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import dalvik.system.PathClassLoader
import javax.inject.Inject

private const val PLUGIN_SOURCE_CLASS = "com.makentoshe.booruchan.extension.source"

private const val USES_FEATURE_PLUGIN = "com.makentoshe.booruchan.extension"

private val PACKAGE_FLAGS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
    PackageManager.GET_CONFIGURATIONS or PackageManager.GET_SIGNING_CERTIFICATES
} else {
    @Suppress("DEPRECATION")
    PackageManager.GET_CONFIGURATIONS or PackageManager.GET_SIGNATURES
}

class GetAllPluginsUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    operator fun invoke(): List<Plugin> {
        // Get all installed packages
        val installedPackages = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getInstalledPackages(PackageManager.PackageInfoFlags.of(PACKAGE_FLAGS.toLong()))
        } else {
            context.packageManager.getInstalledPackages(PACKAGE_FLAGS)
        }

        // Filter only packages that contains our uses-feature flag
        val pluginPackageInfos = installedPackages.filter { packageInfo ->
            packageInfo.reqFeatures.orEmpty().any { it.name == USES_FEATURE_PLUGIN }
        }

        return pluginPackageInfos.map { packageInfo -> mapPackageInfoToPlugin(packageInfo) }
    }

    private fun mapPackageInfoToPlugin(packageInfo: PackageInfo): Plugin {
        // Get application info
        val applicationInfo = try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getApplicationInfo(
                    packageInfo.packageName,
                    PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong())
                )
            } else {
                context.packageManager.getApplicationInfo(
                    packageInfo.packageName,
                    PackageManager.GET_META_DATA
                )
            }
        } catch (error: PackageManager.NameNotFoundException) {
            throw GetPluginException()
        }

        // get source class
        val sourceClass = applicationInfo.metaData.getString(PLUGIN_SOURCE_CLASS)
            ?: throw GetPluginSourceClassException()

        // get source class full name, ready for reflection
        val sourceClassName = sourceClass.trim().let {
            if (it.startsWith(".")) packageInfo.packageName + it else it
        }

        val classLoader = PathClassLoader(applicationInfo.sourceDir, null, context.classLoader)
        val `class` = Class.forName(sourceClassName, false, classLoader)

        return Plugin(
            packageName = packageInfo.packageName,
            sourceClass = `class`,
        )
    }
}
