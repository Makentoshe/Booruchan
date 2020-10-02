package network

interface Filter {
    /** Returns the part of the url */
    fun toUrl(): String
}