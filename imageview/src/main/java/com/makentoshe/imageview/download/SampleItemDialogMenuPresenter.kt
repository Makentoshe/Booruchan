package com.makentoshe.imageview.download

import android.app.Dialog
import android.content.Context
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.imageview.R

class SampleItemDialogMenuPresenter(private val post: Post, private val downloadexecutor: DownloadExecutor) {

    private fun download(context: Context) = context.getString(R.string.download)

    fun bindDialog(context: Context): Dialog {
        val adapter = createAdapter(context)
        val dialoglistener = SimpleDialogInterfaceClickListener()
        dialoglistener.setOnItemClickListener { _, position -> onItemClickListener(context, position, adapter) }
        return AlertDialog.Builder(context).setAdapter(adapter, dialoglistener).create()
    }

    private fun createAdapter(context: Context): ArrayAdapter<String> {
        val elements = arrayOf(download(context))
        val layout = android.R.layout.simple_list_item_1
        return ArrayAdapter(context, layout, elements)
    }

    private fun onItemClickListener(context: Context, position: Int, adapter: ArrayAdapter<String>) {
        when (adapter.getItem(position)) {
            download(context) -> downloadexecutor.download(post)
        }
    }
}