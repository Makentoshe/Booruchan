package com.makentoshe.booruchan.booru.content.view

import android.os.Bundle
import android.support.v4.app.Fragment
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.settings.application.AppSettings
import java.lang.ref.WeakReference

abstract class ContentFragment : Fragment() {

    protected lateinit var booru: Boor
    protected lateinit var appSettings: AppSettings

    abstract fun onSearchStarted(): WeakReference<(String) -> Unit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        booru = arguments!!.getSerializable(booruArg) as Boor
        appSettings = arguments!!.getParcelable(appSettingsArg) as AppSettings
    }

    class ContentFragmentBuilder<F: ContentFragment>(private val fragment: F) {

        fun build(booru: Boor, appSettings: AppSettings): F {
            val args = Bundle()
            args.putSerializable(booruArg, booru)
            args.putParcelable(appSettingsArg, appSettings)
            fragment.arguments = args
            return fragment
        }
    }

    companion object {

        const val booruArg = "BooruArg"
        const val appSettingsArg = "AppSettingsArg"

        fun <F : ContentFragment> newBuilder(`class`: Class<out F>): ContentFragmentBuilder<F> {
            return ContentFragmentBuilder(`class`.newInstance())
        }

    }

}