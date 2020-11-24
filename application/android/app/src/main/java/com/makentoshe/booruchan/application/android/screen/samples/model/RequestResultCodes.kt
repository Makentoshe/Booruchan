package com.makentoshe.booruchan.application.android.screen.samples.model

import android.content.Intent
import com.makentoshe.booruchan.application.android.screen.samples.SampleContentFragment

/** Code for sending error result to the [SampleContentFragment] */
internal const val SAMPLE_CONTENT_ERROR_CODE = 0x00000000

/** Code for storing [Throwable] data in [Intent] */
internal const val SAMPLE_CONTENT_ERROR_DATA = "SampleContentFragment#throwable"

/** Code for sending success result to the [SampleContentFragment] */
internal const val SAMPLE_CONTENT_SUCCESS_CODE = 0x00000001