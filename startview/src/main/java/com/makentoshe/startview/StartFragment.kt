package com.makentoshe.startview

import android.os.Bundle
import android.view.*
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
    private lateinit var contentController: StartFragmentContentController

    /** Controller for the overflow icon */
    private lateinit var overflowController: StartFragmentOverflowController

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
