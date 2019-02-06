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
        println("Put args\n$string\n$argument\n\n\n")
        container[string] = argument
    }

    fun getArgument(string: String): Bundle? {
        val arg = container[string]
        println("Get args\n$string\n$arg\n\n\n")
        return arg
    }

    fun removeArgument(string: String) {
        println("Remove arg $string")
        container.remove(string)
    }

}