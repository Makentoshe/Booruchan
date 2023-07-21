package com.makentoshe.booruchan.screen.boorucontent.ui.bottomsheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.makentoshe.booruchan.screen.boorucontent.domain.SearchRatingUi
import com.makentoshe.library.uikit.foundation.SecondaryText
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
internal fun SearchBottomSheetRatings(
    queryRatings: List<SearchRatingUi>,
    onRatingSelectChange: (Int) -> Unit
) = Column(
    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
) {
    SecondaryText(text = "Rating")

    Row(modifier = Modifier.fillMaxWidth()) {
        var selectedButtonIndex by remember { mutableStateOf(-1) } // -1 is default unselected state for all

        if (queryRatings.count() < 2) Unit else {
            queryRatings.forEachIndexed { index, searchRatingUi ->
                val shape = RoundedCornerShape(
                    topStart = if (index == 0) 8.dp else 0.dp,
                    topEnd = if (index == queryRatings.lastIndex) 8.dp else 0.dp,
                    bottomStart = if (index == 0) 8.dp else 0.dp,
                    bottomEnd = if (index == queryRatings.lastIndex) 8.dp else 0.dp,
                )
                OutlinedButton(
                    modifier = Modifier.weight(weight = 1f).fillMaxWidth()
                        .offset(x = (-1 * index).dp, y = 0.dp)
                        .zIndex(if (selectedButtonIndex == index) 1f else 0f),
                    onClick = {
                        selectedButtonIndex = if (selectedButtonIndex == index) -1 else index
                        onRatingSelectChange(selectedButtonIndex)
                    },
                    shape = shape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedButtonIndex == index) BooruchanTheme.colors.opaque else BooruchanTheme.colors.background,
                        contentColor = if (selectedButtonIndex == index) BooruchanTheme.colors.background else BooruchanTheme.colors.foreground,
                    ),
                    border = BorderStroke(width = 1.dp, color = BooruchanTheme.colors.opaque)
                ) {
                    Text(searchRatingUi.title)
                }
            }
        }
    }
}
