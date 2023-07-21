package com.makentoshe.booruchan.screen.boorucontent.ui.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makentoshe.booruchan.screen.boorucontent.domain.SearchTagCategory
import com.makentoshe.booruchan.screen.boorucontent.domain.SearchTagUi
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.SearchTagChip
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentBottomSheetState
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenEvent
import com.makentoshe.library.uikit.foundation.SecondaryText
import com.makentoshe.library.uikit.layout.ChipGroup
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
internal fun SearchBottomSheetTags(
    sheetState: BoorucontentBottomSheetState,
    screenEvent: (BoorucontentScreenEvent) -> Unit,
) = Column {

    val generalTags = remember(key1 = sheetState.queryTags) {
        sheetState.queryTags.filter { it.category is SearchTagCategory.General }
    }
    if (generalTags.isNotEmpty()) {
        SearchBottomSheetGeneralTags(
            modifier = Modifier.padding(horizontal = 16.dp),
            generalTags = generalTags,
            onCloseChipIconClick = {
                screenEvent(BoorucontentScreenEvent.RemoveSearchTag(it))
            }
        )
    }
}

@Composable
private fun SearchBottomSheetGeneralTags(
    generalTags: List<SearchTagUi>,
    modifier: Modifier = Modifier,
    onCloseChipIconClick: (SearchTagUi) -> Unit,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(8.dp),
) {
    SecondaryText(text = "General", color = BooruchanTheme.colors.foreground)

    ChipGroup(
        modifier = Modifier.fillMaxWidth(),
        spacing = 4.dp
    ) {
        generalTags.forEach { tag ->
            SearchTagChip(
                modifier = Modifier.height(32.dp),
                searchTagUi = tag,
                onCloseIconClick = { onCloseChipIconClick(tag) })
        }
    }
}
