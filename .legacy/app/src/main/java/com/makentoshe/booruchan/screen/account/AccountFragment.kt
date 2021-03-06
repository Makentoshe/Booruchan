package com.makentoshe.booruchan.screen.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.screen.booru.inflator.BooruToolbarUiInflater
import com.makentoshe.booruchan.model.arguments
import org.jetbrains.anko.AnkoContext

class AccountFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return AccountUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AccountUiToolbarInflator(booru).accept(view)

        parentFragment?.view?.findViewById<DrawerLayout>(R.id.booru_drawer)?.let {
            BooruToolbarUiInflater(it).accept(view)
        }
    }

    companion object {
        private const val BOORU = "Booru"

        fun create(booru: Booru) = AccountFragment().apply {
            this.booru = booru
        }
    }
}