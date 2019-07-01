package com.makentoshe.settings

/**
 * Interface for controlling ans storing setting with type [V].
 * It can be database or shared preferences or something else.
 */
interface SettingController<V> {

    /**
     * Put new [value] by [key]
     */
    fun put(key: String, value: V)

    /**
     * Put new value from [setting] by key
     */
    fun put(setting: Setting<V>) = put(setting.title, setting.value)

    /**
     * Return value by [key]. If value does not defined - it will be created with the default value.
     * @return a setting value by a [key] or [default] if value does not exists.
     */
    fun get(key: String, default: V): V

}
