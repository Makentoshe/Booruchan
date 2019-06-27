package com.makentoshe.settingsview.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.settings.common.SettingsBuilder
import com.makentoshe.settingsview.viewmodel.DefaultSettingsFragmentViewModel
import com.makentoshe.settingsview.presenter.DefaultSettingsPresenter
import com.makentoshe.settingsview.view.DefaultSettingsFragmentUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext

class DefaultSettingsFragment : Fragment() {

    /** SHOULD NOT BEING USED. INSTANCE WILL BE null AFTER FRAGMENT RECREATION.
     * USE [SettingsBuilder] INSTANCE FROM [DefaultSettingsFragmentViewModel] component */
    private var settingsBuilder: SettingsBuilder? = null

    /** Contains [io.reactivex.disposables.Disposable] objects for release in [onDestroy] lifecycle event */
    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DefaultSettingsFragmentUi().createView(AnkoContext.create(requireContext(), false))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // build viewmodel
        val factory = DefaultSettingsFragmentViewModel.Factory(settingsBuilder)
        val viewmodel = ViewModelProviders.of(this, factory)[DefaultSettingsFragmentViewModel::class.java]
        // build presenter
        val presenter =
            DefaultSettingsPresenter(disposables, viewmodel, requireFragmentManager())
        // bind nsfw setting stroke
        val nsfwRoot = view.findViewById<View>(com.makentoshe.settingsview.R.id.nsfw_setting)
        val nsfwTrigger = view.findViewById<CheckBox>(com.makentoshe.settingsview.R.id.nsfw_setting_target)
        presenter.bindNsfwSetting(nsfwRoot, nsfwTrigger)
    }

    /** Release [disposables] */
    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        /** Creates a [DefaultSettingsFragment] instance used [SettingsBuilder] */
        fun build(settingsBuilder: SettingsBuilder): DefaultSettingsFragment {
            val fragment = DefaultSettingsFragment()
            fragment.settingsBuilder = settingsBuilder
            return fragment
        }

        /** Returns a fragment title used in a tab layout */
        fun title(context: Context): String {
            return context.getString(com.makentoshe.settingsview.R.string.default_setting)
        }

    }
}
