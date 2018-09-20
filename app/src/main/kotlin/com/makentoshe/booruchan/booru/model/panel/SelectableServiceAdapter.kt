package com.makentoshe.booruchan.booru.model.panel

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.makentoshe.booruchan.R
import org.jetbrains.anko.backgroundResource

class SelectableServiceAdapter(context: Context, @LayoutRes resource: Int, objects: List<CharSequence>)
    : ArrayAdapter<CharSequence>(context, resource, objects) {

    private var onStart = true

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = super.getView(position, convertView, parent)
        if (position == 0 && onStart) {
            view.backgroundResource = R.color.MaterialIndigo200
        }
        return view
    }

}