package com.makentoshe.booruchan.screen.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import org.jetbrains.anko.AnkoContext

class SampleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SampleUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        fixFragmentManagerMemoryLeak(childFragmentManager)
    }


    /*
    * Method fixes a strange memory leak. The fragments in fragment manager is not
    * releases after onDestroy and onDetach events. They still alive after rotation event
    * and only parent fragment removing fix the problem. This method removes fragments
    * manually from fragment holder using fragment manager. */
    private fun fixFragmentManagerMemoryLeak(fragmentManager: FragmentManager) {
        val transaction = fragmentManager.beginTransaction()
        fragmentManager.fragments.forEach {
            transaction.remove(it)
        }
        transaction.commit()
        println(fragmentManager.fragments)
    }
}