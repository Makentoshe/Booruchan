package com.makentoshe.startview

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.api.repository.BooruRepository
import com.makentoshe.settings.SettingsBuilder
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

/**
 * Fragment contains start screen logic
 */
class StartFragment : Fragment() {

    /** Disposables must be released on destroy lifecycle event */
    private val disposables = CompositeDisposable()

    /**
     * Navigator for performing a navigation action. Initialized only on first fragment creation
     * and puts into viewmodel component. After rotation recreations this variable does not needed.
     */
    private var navigator: StartFragmentNavigator? = null

    /** Viewmodel component contains a navigator instance */
    private lateinit var viewmodel: StartFragmentViewModel

    /** Init viewmodel component */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val factory = StartFragmentViewModel.Factory(navigator)
        viewmodel = ViewModelProviders.of(this, factory)[StartFragmentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return StartFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val settingsBuilder = SettingsBuilder()
        // creates presenter component
        val presenter = StartFragmentPresenter(disposables, viewmodel,
            BooruRepository(), settingsBuilder)
        // binds a list view
        val listview = view.find<ListView>(com.makentoshe.startview.R.id.start_content_listview)
        presenter.bindListView(listview)
        // binds an overflow icon
        val overflowIcon = view.find<View>(com.makentoshe.startview.R.id.start_toolbar_overflow)
        presenter.bindOverflowIcon(overflowIcon)
    }

    /** Release disposables */
    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        /**
         * Creates a StartFragment instance
         * @param navigator is a navigator interfaces performs navigation from start screen to other
         */
        fun build(navigator: StartFragmentNavigator): Fragment {
            val fragment = StartFragment()
            fragment.navigator = navigator
            return fragment
        }
    }
}
