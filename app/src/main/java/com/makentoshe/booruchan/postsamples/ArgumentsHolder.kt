package com.makentoshe.booruchan.postsamples

import android.os.Bundle

/* Static singleton for holding fragments arguments avoiding default arguments holding implementation
* which */
object ArgumentsHolder {

    /* Arguments will be contain in hash map.
     * String - represents a single unique fragment instance.
     * Fragments can be destroyed, so it's a bad idea to save fragments arguments using Fragment.toString
     * Bundle - fragment arguments. */
    private val container = HashMap<String, Bundle>()

    /* Returns the fragment arguments by string */
    operator fun get(key: String): Bundle? = container[key]

    /* Puts fragment arguments into storage */
    operator fun set(key: String, value: Bundle) = container.put(key, value)

    /* Removes fragment arguments from the storage */
    fun remove(key: String) = container.remove(key)
}