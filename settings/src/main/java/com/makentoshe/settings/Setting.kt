package com.makentoshe.settings

/**
 * Describes a single setting value with type [T].
 */
interface Setting<T> {

    var title: String

    /** A setting value with type [T] */
    var value: T
}

