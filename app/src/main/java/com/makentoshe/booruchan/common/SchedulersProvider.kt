package com.makentoshe.booruchan.common

import io.reactivex.Scheduler

/**
 * Class for providing schedulers to any classes which uses Rx.
 *
 * @param foreground is a scheduler for a foreground tasks such as ui update.
 * @param background is a scheduler for a background tasks such as downloading.
 */
data class SchedulersProvider(val foreground: Scheduler, val background: Scheduler)
