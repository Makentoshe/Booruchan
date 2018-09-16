package com.makentoshe.booruchan.booru

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class BooruViewModelFactoryTest {

    @Test
    fun `should create BooruViewModel`() {
        val activity = Robolectric.setupActivity(AppCompatActivity::class.java)
        val viewModel = ViewModelProviders
                .of(activity, BooruViewModelFactory(Gelbooru()))[BooruViewModel::class.java]
        assertNotNull(viewModel)
        assertNotNull(viewModel.booru)
        assertTrue(viewModel.booru is Gelbooru)
    }

    class TestViewModel: ViewModel()

    @Test
    fun `should create a`() {
        val activity = Robolectric.setupActivity(AppCompatActivity::class.java)
        val viewModel = ViewModelProviders
                .of(activity, BooruViewModelFactory(Gelbooru()))[TestViewModel::class.java]
        assertNotNull(viewModel)
    }

}