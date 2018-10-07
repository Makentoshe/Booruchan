package com.makentoshe.booruchan.booru.content.comments.vertical.pager

import com.makentoshe.booruchan.booru.content.comments.vertical.pager.CommentsViewModel
import com.makentoshe.booruchan.common.settings.application.AppSettings
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CommentsViewModelTest {

    @Test
    fun appSettingsAccess() {
        val appSettings = AppSettings()
        val viewModel = CommentsViewModel(appSettings)
        assertNotNull(viewModel.appSettings)
    }

}