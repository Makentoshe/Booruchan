package com.makentoshe.booruchan.screen.boorucontent.ui.foundation.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makentoshe.library.uikit.foundation.PrimaryTextBold
import com.makentoshe.library.uikit.foundation.SecondaryText

@Composable
internal fun BoorucontentErrorLayout() = Column(
    modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    PrimaryTextBold(
        text = "There is a ###### error",
        textAlign = TextAlign.Center,
    )

    Spacer(modifier = Modifier.height(16.dp))

    SecondaryText(
        text = "Maybe it is a network error, or something else. The message will be appeared here",
        textAlign = TextAlign.Center,
    )

    Spacer(modifier = Modifier.height(16.dp))

    TextButton(
        onClick = { println("Retry") },
    ) {
        PrimaryTextBold(
            text = "Retry",
            textAlign = TextAlign.Center,
        )
    }

}
