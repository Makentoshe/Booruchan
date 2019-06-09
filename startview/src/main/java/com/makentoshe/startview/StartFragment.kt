package com.makentoshe.startview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.makentoshe.api.BooruRepository
import com.makentoshe.settings.model.realm.RealmBooleanSettingController
import io.realm.Realm
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

/**
 * Fragment contains start screen logic
 */
class StartFragment : Fragment() {

    /** Controller for the listview */
    private var contentController: StartFragmentContentController
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(CONTENTC, value)
        get() = arguments!!.get(CONTENTC) as StartFragmentContentController

    /** Controller for the overflow icon */
    private var overflowController: StartFragmentOverflowController
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(OVERFLOWC, value)
        get() = arguments!!.get(OVERFLOWC) as StartFragmentOverflowController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return StartFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listview = view.find<ListView>(com.makentoshe.startview.R.id.start_content_listview)
        contentController.bindView(listview)

        val overflowIcon = view.find<View>(com.makentoshe.startview.R.id.start_toolbar_overflow)
        overflowController.bindView(overflowIcon)
    }

    companion object {
        private const val CONTENTC = "ContentController"
        private const val OVERFLOWC = "OverflowController"

        /**
         * Creates a StartFragmentInstance
         * @param navigator is a navigator interfaces performs navigation from start screen to other
         */
        fun build(navigator: StartFragmentNavigator): Fragment {
            val fragment = StartFragment()

            val booruRepository = BooruRepository()
            val settingController = RealmBooleanSettingController(Realm.getDefaultConfiguration()!!)
            fragment.contentController = StartFragmentContentController(booruRepository, settingController, navigator)

            fragment.overflowController = StartFragmentOverflowController(navigator)

            return fragment
        }
    }

}
