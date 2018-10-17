package com.makentoshe.booruchan.booru.content.posts

import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.sample.view.SampleActivity

class BooruPostNavigator {

    fun startSampleActivity(activity: FragmentActivity, itemId: Int, booru: Boor, tags: String) {
        val intent = Intent(activity, SampleActivity::class.java)
        intent.putExtra(SampleActivity.BOORU_EXTRA, booru)
        intent.putExtra(SampleActivity.START_ID, itemId)
        intent.putExtra(SampleActivity.TAGS_EXTRA, tags)
        activity.startActivity(intent)
    }

}