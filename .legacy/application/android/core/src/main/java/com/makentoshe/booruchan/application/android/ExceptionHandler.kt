package com.makentoshe.booruchan.application.android

import android.content.Context
import android.util.Log
import com.makentoshe.booruchan.application.android.core.R
import com.makentoshe.booruchan.application.core.arena.ArenaStorageException
import io.ktor.client.features.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException

interface ExceptionHandler {

    /** Consumes [exception] and returns an [Entry] */
    fun handleException(exception: Throwable?): Entry

    // TODO add Generic types for exception
    /** [title] describes a main error cause and [message] is just a description */
    data class Entry(val title: String, val message: String, val exception: Throwable)
}

class ExceptionHandlerImpl(private val context: Context) : ExceptionHandler {

    override fun handleException(exception: Throwable?): ExceptionHandler.Entry = when (exception) {
        is ArenaStorageException -> handleArenaStorageException(exception)
        is SSLHandshakeException -> handleSSLHandshakeException(exception)
        else -> handleUnknownException(exception)
    }

    private fun handleArenaStorageException(exception: ArenaStorageException) =
        when (val cause = exception.cause) {
            is SSLPeerUnverifiedException -> handleArenaStorageSSlPeerUnverifiedException(cause)
            is UnknownHostException -> handleArenaStorageUnknownHostException(cause)
            is SSLHandshakeException -> handleSSLHandshakeException(cause)
            is ClientRequestException -> handleClientRequestException(cause)
            is SocketTimeoutException -> handleSocketTimeoutException(cause)
            else -> handleUnknownArenaStorageException(exception)
        }

    /** internet connection disabled */
    private fun handleArenaStorageUnknownHostException(exception: UnknownHostException): ExceptionHandler.Entry {
        val title = context.getString(R.string.exception_handler_network)
        val description = context.getString(R.string.exception_handler_network_unknown_host)
        return ExceptionHandler.Entry(title, description, exception)
    }

    /** provider blocks the host */
    private fun handleArenaStorageSSlPeerUnverifiedException(exception: SSLPeerUnverifiedException): ExceptionHandler.Entry {
        val title = context.getString(R.string.exception_handler_network)
        val description = context.getString(R.string.exception_handler_network_sslpeer_unverified)
        return ExceptionHandler.Entry(title, description, exception)
    }

    /** proxy connection outdated */
    private fun handleSSLHandshakeException(exception: SSLHandshakeException): ExceptionHandler.Entry {
        val title = context.getString(R.string.exception_handler_proxy)
        val message = context.getString(R.string.exception_handler_proxy_handshake)
        return ExceptionHandler.Entry(title, message, exception)
    }

    // TODO server requests to resolve captcha event
    private fun handleClientRequestException(exception: ClientRequestException): ExceptionHandler.Entry {
        val title = context.getString(R.string.exception_handler_proxy)
        val message = context.getString(R.string.exception_handler_proxy_cloudflare)
        return ExceptionHandler.Entry(title, message, exception)
    }

    /** proxy connection expired cause */
    private fun handleSocketTimeoutException(exception: SocketTimeoutException): ExceptionHandler.Entry {
        val title = context.getString(R.string.exception_handler_proxy)
        val message = context.getString(R.string.exception_handler_proxy_expire)
        return ExceptionHandler.Entry(title, message, exception)
    }

    private fun handleUnknownArenaStorageException(exception: ArenaStorageException): ExceptionHandler.Entry {
        Log.e("ExceptionHandler#Arena", exception.toString())

        val title = context.getString(R.string.exception_handler_unknown_cache)
        val description = exception.toString()
        return ExceptionHandler.Entry(title, description, exception)
    }

    private fun handleUnknownException(exception: Throwable?): ExceptionHandler.Entry {
        Log.e("ExceptionHandler#Unk", exception.toString())

        val title = context.getString(R.string.exception_handler_unknown)
        val description = exception.toString()
        return ExceptionHandler.Entry(title, description, exception ?: Exception("null"))
    }
}
