package com.makentoshe.booruchan

import android.os.Bundle
import android.os.TransactionTooLargeException

/**
 * Static singleton for holding arguments which is used between fragments for avoiding [TransactionTooLargeException].
 */
object ArgumentsHolder {

    /**
     * Fragment's arguments contains here. The key is a string.
     * For each fragment it must be different
     */
    private val container = HashMap<String, Bundle>()

    fun putArgument(string: String, argument: Bundle) {
        container[string] = argument
    }

    fun getArgument(fragment: String) = container[fragment]

    fun removeArgument(fragment: String) = container.remove(fragment)

}