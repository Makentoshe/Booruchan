package com.makentoshe.booruchan.library.logging

import android.app.Application
import androidx.lifecycle.ViewModel
import timber.log.Timber

private const val FILENAME = "LoggerExtensionsKt"
private const val DELIMITER = '.'

fun Application.initializeDebugLogger() {
    Timber.plant(Timber.DebugTree())
}



fun logInfo(subject: Any, fingerprint: LogFingerprint, message: String) {
    Timber.tag(subject.getTimberTag(fingerprint)).i(message)
}

fun logInfo(string: String, fingerprint: LogFingerprint, message: String) {
    Timber.tag(fingerprint.title.plus(string)).i(message)
}

fun ViewModel.internalLogInfo(message: String) {
    logInfo(this, LogFingerprint.Internal, message)
}

fun screenLogInfo(subject: Any, message: String) {
    logInfo(subject, LogFingerprint.Screen, message)
}

fun screenLogInfo(string: String, message: String) {
    logInfo(string = string, LogFingerprint.Screen, message)
}



fun logError(subject: Any, fingerprint: LogFingerprint, throwable: Throwable) {
    Timber.tag(subject.getTimberTag(fingerprint)).e(throwable)
}

fun logError(subject: Any, fingerprint: LogFingerprint, message: String) {
    Timber.tag(subject.getTimberTag(fingerprint)).e(message)
}

fun ViewModel.internalLogError(throwable: Throwable) {
    logError(this, LogFingerprint.Internal, throwable)
}

fun ViewModel.internalLogError(message: String) {
    logError(this, LogFingerprint.Internal, message)
}



fun logWarn(subject: Any, fingerprint: LogFingerprint, message: String) {
    Timber.tag(subject.getTimberTag(fingerprint)).w(message)
}

fun ViewModel.internalLogWarn(message: String) {
    logWarn(this, LogFingerprint.Internal, message)
}

internal fun Any.getTimberTag(fingerprint: LogFingerprint): String {
    val className = javaClass.canonicalName ?: Throwable().stackTrace.first { element ->
        !element.className.contains(FILENAME)
    }.className

    val classTitle = className.substringAfterLast(DELIMITER)

    return fingerprint.title.plus(classTitle.lowercase())
}

