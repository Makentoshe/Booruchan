package com.makentoshe.booruchan.screen.samples.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.samples.controller.SampleSwipeBottomBarController
import com.makentoshe.booruchan.screen.samples.controller.SampleSwipeController
import com.makentoshe.booruchan.screen.samples.view.SampleSwipeUi
import org.jetbrains.anko.AnkoContext
import org.koin.androidx.scope.currentScope
import org.koin.core.parameter.parametersOf
import java.io.Serializable

/**
 * Fragment for containing a vertical swiping functional.
 */
class SampleSwipeFragment : Fragment() {

    init {
        //provide fragment instance to the scope
        currentScope.get<SampleSwipeFragment> { parametersOf(this) }
    }

    //displaying starts from current position
    private var position: Int
        get() = arguments!!.getInt(POSITION)
        set(value) = arguments().putInt(POSITION, value)

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private val bottomBarController by currentScope.inject<SampleSwipeBottomBarController> {
        parametersOf(booru, tags)
    }

    private val contentController by currentScope.inject<SampleSwipeController> {
        parametersOf(booru, tags, position)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SampleSwipeUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        contentController.bindView(view)
        bottomBarController.bindView(view)
    }

    companion object {
        private const val POSITION = "Position"
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun create(position: Int, booru: Booru, tags: Set<Tag>) = SampleSwipeFragment().apply {
            this.position = position
            this.booru = booru
            this.tags = tags
        }
    }
}

