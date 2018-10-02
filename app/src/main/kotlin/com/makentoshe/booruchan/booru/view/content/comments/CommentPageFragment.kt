package com.makentoshe.booruchan.booru.view.content.comments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoContext
import java.util.*

class CommentPageFragment: Fragment() {

    private var page = -1
    private var color = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments?.getInt(ARG_PAGE)!!
        val rnd = Random()
        color = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return CommentPageFragmentUI(page, color).createView(AnkoContext.create(activity!!, this))
    }

    companion object {
        private const val ARG_PAGE = "ArgPage"

        fun new(page: Int): CommentPageFragment {
            val fragment = CommentPageFragment()
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            fragment.arguments = args
            return fragment
        }
    }

}