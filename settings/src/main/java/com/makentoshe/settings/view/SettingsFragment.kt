package com.makentoshe.settings.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.settings.R
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.titleResource
import org.jetbrains.anko.appcompat.v7.toolbar

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsFragmentUi().createView(AnkoContext.create(requireContext(), false))
    }
}

class SettingsFragmentUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        relativeLayout {
            SettingsFragmentUiToolbar().createView(AnkoContext.createDelegate(this))
        }
    }
}

class SettingsFragmentUiToolbar : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        relativeLayout {
            backgroundColor = Color.CYAN
            toolbar {
                titleResource = R.string.app_name
            }.lparams(matchParent, matchParent)
        }.lparams(matchParent, resources.getDimension(R.dimen.toolbar_height).toInt()) {
            alignParentTop()
        }
    }
}
