package com.makentoshe.booruchan.sample.view

import android.os.Bundle
import android.support.v4.app.Fragment

class FragmentPage: Fragment() {

    companion object {
        private const val ARG_POSITION = "PositionArg"

        fun new(position: Int): FragmentPage {
            val fragment = FragmentPage()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

}