package com.makentoshe.screen.boorulist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makentoshe.booruchan.library.resources.R
import com.makentoshe.library.uikit.foundation.TitleText
import com.makentoshe.library.uikit.theme.BooruchanTheme
import com.makentoshe.screen.boorulist.viewmodel.BoorulistEvent
import com.makentoshe.screen.boorulist.viewmodel.BoorulistState
import com.makentoshe.screen.boorulist.viewmodel.BoorulistStateContent

@Composable
internal fun BoorulistScreenUi(
    state: BoorulistState,
    event: (BoorulistEvent) -> Unit,
) = Scaffold(
    topBar = { BoorulistTopBar() },
    content = { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            BoorulistScreenScaffoldContent(state = state, event = event)
        }
    }
)

@Composable
private fun BoorulistTopBar() = Column(modifier = Modifier.fillMaxWidth()) {
    Box(
        modifier = Modifier.fillMaxWidth().height(56.dp).padding(start = 16.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        TitleText(text = stringResource(id = R.string.app_name))
    }

    Divider(color = BooruchanTheme.colors.separator, thickness = 1.dp)
}

@Composable
private fun BoorulistScreenScaffoldContent(
    state: BoorulistState,
    event: (BoorulistEvent) -> Unit,
) = when (state.content) {
    BoorulistStateContent.Loading -> {
        BoorulistScreenUiLoading()
    }
    is BoorulistStateContent.Content -> {
        BoorulistScreenUiContent(state = state.content, event = event)
    }
}