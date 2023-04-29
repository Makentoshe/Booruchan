package com.makentoshe.booruchan.screen.boorucontent.ui

import androidx.compose.runtime.Composable
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.RecyclerViewVerticalSpannedGrid
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentState
import com.makentoshe.library.uikit.extensions.collectLatestInComposable

@Composable
internal fun BoorucontentContent(state: BoorucontentState) {
    RecyclerViewVerticalSpannedGrid(state)
}
