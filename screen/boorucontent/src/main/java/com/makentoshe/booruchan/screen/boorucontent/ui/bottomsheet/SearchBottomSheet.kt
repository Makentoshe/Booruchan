package com.makentoshe.booruchan.screen.boorucontent.ui.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenEvent
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenState

@Composable
fun SearchBottomSheetContent(
    screenState: BoorucontentScreenState,
    screenEvent: (BoorucontentScreenEvent) -> Unit,
    onSearchButtonClicked: () -> Unit,
) = Column(
    modifier = Modifier.fillMaxSize(),
) {
    SearchBottomSheetAutoComplete(
        queryAutoComplete = screenState.bottomSheetState.queryAutocomplete,
        onAutoComplete = { screenEvent(BoorucontentScreenEvent.AutoCompleteTag(it))},
        onDropDownClick = { index, tag -> screenEvent(BoorucontentScreenEvent.AddSearchTag(tag, index))}
    )

    Spacer(modifier = Modifier.height(16.dp).fillMaxWidth())

    Divider(modifier = Modifier.fillMaxWidth())

    Spacer(modifier = Modifier.height(16.dp).fillMaxWidth())

    SearchBottomSheetRatings(
        queryRatings = screenState.bottomSheetState.queryRatings,
        onRatingSelectChange = { index ->
            // TODO implement logic
        }
    )

    Spacer(modifier = Modifier.height(16.dp).fillMaxWidth())

    SearchBottomSheetTags(sheetState = screenState.bottomSheetState, screenEvent = screenEvent)

    Button(onClick = { onSearchButtonClicked() }) {
        Text("Search")
    }
}
