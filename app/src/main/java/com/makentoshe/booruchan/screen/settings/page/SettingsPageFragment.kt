package com.makentoshe.booruchan.screen.settings.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.model.arguments
import org.jetbrains.anko.AnkoContext
import org.koin.androidx.scope.currentScope

/* Container for concrete fragments settings */
class SettingsPageFragment : Fragment() {

    private var position: Int
        set(value) = arguments().putInt(POSITION, value)
        get() = arguments!!.getInt(POSITION)

    private val contentController by currentScope.inject<ContentController>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsPageUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        contentController.bindView(position, childFragmentManager)
    }

    companion object {
        private const val POSITION = "Position"
        fun create(position: Int) = SettingsPageFragment().apply {
            this.position = position
        }
    }
}
