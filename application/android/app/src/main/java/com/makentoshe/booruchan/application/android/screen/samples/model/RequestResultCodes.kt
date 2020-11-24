package com.makentoshe.booruchan.application.android.screen.samples.model

import android.content.Intent
import com.makentoshe.booruchan.application.android.screen.samples.SampleContentFragment

/**
 * Code for sending error result to the [SampleContentFragment].
 * In this code the data should contain Throwable by [SAMPLE_CONTENT_ERROR_DATA] key.
 */
internal const val SAMPLE_CONTENT_ERROR_CODE = 0x00000000

/** Code for storing [Throwable] data in [Intent] when [SAMPLE_CONTENT_ERROR_CODE] returns. */
internal const val SAMPLE_CONTENT_ERROR_DATA = "SampleContentFragment#throwable"

/** Code for sending success content loading signal to the [SampleContentFragment] */
internal const val SAMPLE_CONTENT_SUCCESS_CODE = 0x00000001