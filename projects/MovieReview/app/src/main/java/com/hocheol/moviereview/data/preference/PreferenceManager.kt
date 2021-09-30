package com.hocheol.moviereview.data.preference

interface PreferenceManager {

    fun getString(key: String): String?

    fun putString(key: String, value: String)

}
