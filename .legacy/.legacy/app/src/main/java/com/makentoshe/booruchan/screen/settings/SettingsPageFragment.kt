package com.makentoshe.booruchan.screen.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.settings.model.SettingsScreenBuilder
import com.makentoshe.booruchan.screen.settings.view.SettingsPageUi
import org.jetbrains.anko.AnkoContext

/* Container for concrete fragments settings */
class SettingsPageFragment : Fragment() {

    private var position: Int
        set(value) = arguments().putInt(POSITION, value)
        get() = arguments!!.getInt(POSITION)

    private var screenBuilder: SettingsScreenBuilder
        set(value) = arguments().putSerializable(SCREEN_BUILDER, value)
        get() = arguments!!.get(SCREEN_BUILDER) as SettingsScreenBuilder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsPageUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val fragment = screenBuilder.build(position).fragment
        setConcreteFragment(fragment)
    }

    private fun setConcreteFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().add(R.id.settings_container, fragment).commit()
    }

    companion object {
        private const val POSITION = "Position"
        private const val SCREEN_BUILDER = "ScreenBuilder"
        fun create(position: Int, screenBuilder: SettingsScreenBuilder) = SettingsPageFragment().apply {
            this.position = position
            this.screenBuilder = screenBuilder
        }
    }
}