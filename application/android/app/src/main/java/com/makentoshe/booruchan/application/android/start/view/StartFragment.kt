package com.makentoshe.booruchan.application.android.start.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.start.model.StartRecyclerAdapter
import com.makentoshe.booruchan.application.android.start.navigation.StartNavigation
import context.BooruContext
import kotlinx.android.synthetic.main.fragment_start.*
import toothpick.ktp.delegate.inject

class StartFragment : Fragment() {

    companion object {
        fun build(): StartFragment {
            return StartFragment()
        }
    }

    private val booruContexts by inject<List<BooruContext>>()
    private val navigation by inject<StartNavigation>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragment_start_recycler.adapter = StartRecyclerAdapter(booruContexts, navigation)
        val itemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.item_decorator_divider, requireContext().theme))
        fragment_start_recycler.addItemDecoration(itemDecoration)
    }
}
