package com.makentoshe.booruchan.booru.model

import android.content.Intent
import com.makentoshe.booruchan.api.Boor
import com.makentoshe.booruchan.booru.view.BooruActivity

class Extractor(intent: Intent) {

    val boor: Boor = intent.getSerializableExtra(BooruActivity.BOOR_EXTRA) as Boor

}