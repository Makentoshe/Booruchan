package com.makentoshe.booruchan.library.logging

import android.app.Application
import timber.log.Timber

private const val FILENAME = "LoggerExtensionsKt"
private const val DELIMITER = '.'

fun Application.initializeDebugLogger() {
    Timber.plant(Timber.DebugTree())
}

fun logInfo(subject: Any, fingerprint: LogFingerprint, message: String) {
    Timber.tag(subject.getTimberTag(fingerprint)).i(message)
}

fun logError(subject: Any, fingerprint: LogFingerprint, throwable: Throwable) {
    Timber.tag(subject.getTimberTag(fingerprint)).e(throwable)
}

fun logWarn(subject: Any, fingerprint: LogFingerprint, message: String) {
    Timber.tag(subject.getTimberTag(fingerprint)).w(message)
}

internal fun Any.getTimberTag(fingerprint: LogFingerprint): String {
    val className = javaClass.canonicalName ?: Throwable().stackTrace.first { element ->
        !element.className.contains(FILENAME)
    }.className

    val classTitle = className.substringAfterLast(DELIMITER)

    return fingerprint.title.plus(classTitle.lowercase())
}

