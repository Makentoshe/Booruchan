package com.makentoshe.settings.screen.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.settings.model.realm.RealmBooleanSettingController
import com.makentoshe.settings.screen.view.DefaultSettingsFragmentUi
import com.makentoshe.settings.screen.controller.NsfwController
import com.makentoshe.settings.screen.controller.NsfwControllerAlert
import io.realm.Realm
import io.realm.RealmConfiguration
import org.jetbrains.anko.AnkoContext

class DefaultSettingsFragment : Fragment() {

    lateinit var nsfwControllerFactory: NsfwController.Factory
        private set

    lateinit var nsfwControllerAlertFactory: NsfwControllerAlert.Factory
        private set

    lateinit var realmConfiguration: RealmConfiguration
        private set

    private val nsfwControllerAlert by lazy {
        nsfwControllerAlertFactory.build(requireFragmentManager())
    }

    private val nsfwController by lazy {
        val controller = RealmBooleanSettingController(realmConfiguration)
        nsfwControllerFactory.build(controller, nsfwControllerAlert)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DefaultSettingsFragmentUi().createView(AnkoContext.create(requireContext(), false))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        nsfwController.bindView(view)
    }

    class Factory {

        fun build(): DefaultSettingsFragment {
            return build(Realm.getDefaultConfiguration()!!)
        }

        fun build(configuration: RealmConfiguration): DefaultSettingsFragment {
            val fragment = DefaultSettingsFragment()
            fragment.nsfwControllerFactory = NsfwController.Factory()
            fragment.nsfwControllerAlertFactory = NsfwControllerAlert.Factory()
            fragment.realmConfiguration = configuration
            return fragment
        }

        fun title(context: Context): String {
            return context.getString(com.makentoshe.settings.R.string.default_setting)
        }

    }
}
