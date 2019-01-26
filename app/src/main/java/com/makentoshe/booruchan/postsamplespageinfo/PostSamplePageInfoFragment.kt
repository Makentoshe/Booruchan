package com.makentoshe.booruchan.postsamplespageinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import org.jetbrains.anko.backgroundColorResource

class PostSamplePageInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(context).apply {
            text = "Info"
            backgroundColorResource = Booruchan.INSTANCE.style.background.backgroundColorRes
        }
    }
}