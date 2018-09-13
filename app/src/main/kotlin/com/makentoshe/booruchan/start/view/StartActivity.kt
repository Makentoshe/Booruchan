package com.makentoshe.booruchan.start.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.start.StartViewModel
import org.jetbrains.anko.*

class StartActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StartActivityUI(getAppSettings().getStyle()).setContentView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_start_overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.itemId?.let {
            ViewModelProviders.of(this)[StartViewModel::class.java]
                    .clickOnOverflow(it, this)
            return true
        }
        return false
    }

}
