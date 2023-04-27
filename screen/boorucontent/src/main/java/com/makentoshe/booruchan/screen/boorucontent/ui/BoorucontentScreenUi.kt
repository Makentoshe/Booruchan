package com.makentoshe.booruchan.screen.boorucontent.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentContentState
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentState
import com.makentoshe.library.uikit.foundation.IndeterminateProgressBar
import com.makentoshe.library.uikit.foundation.PrimaryText

@Composable
internal fun BoorucontentScreenUi(
    state: BoorucontentState,
) = Scaffold(
    topBar = { BoorucontentTopBar(state) }
) { paddingValues ->
    Column(modifier = Modifier.padding(paddingValues)) {
        BoorucontentContent(state = state)
    }
}

