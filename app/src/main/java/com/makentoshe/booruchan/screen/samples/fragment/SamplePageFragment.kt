package com.makentoshe.booruchan.screen.samples.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.samples.SampleModule
import com.makentoshe.booruchan.screen.samples.SamplePageViewModel
import com.makentoshe.booruchan.screen.samples.controller.SamplePageContentController
import com.makentoshe.booruchan.screen.samples.view.SamplePageUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import java.io.Serializable

class SamplePageFragment : Fragment() {

    private val disposables: CompositeDisposable

    init {
        //provide current fragment instance to the scope
        currentScope.get<SamplePageFragment> { parametersOf(this) }
        //provide composite disposables to the scope
        this.disposables = currentScope.get(named(SampleModule.PAGE_DISPOSABLE)) {
            parametersOf(CompositeDisposable())
        }
    }

    private var position: Int
        get() = arguments!!.getInt(POSITION)
        set(value) = arguments().putInt(POSITION, value)

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private val viewModel by viewModel<SamplePageViewModel> {
        parametersOf(booru, tags, position, disposables)
    }

    private val contentController by currentScope.inject<SamplePageContentController>()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.init()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SamplePageUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        contentController.bindView(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    companion object {
        private const val POSITION = "Position"
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun create(position: Int, booru: Booru, tags: Set<Tag>) = SamplePageFragment().apply {
            this.position = position
            this.booru = booru
            this.tags = tags
        }
    }
}