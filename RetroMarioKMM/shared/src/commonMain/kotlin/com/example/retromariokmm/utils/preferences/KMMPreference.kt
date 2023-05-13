package com.example.retromariokmm.utils.preferences

import com.example.retromariokmm.domain.models.RetroUser

class KMMPreference(private val context: KMMContext) {

    fun put(key: String, value: Int) {
        context.putInt(key, value)
    }

    fun put(key: String, value: String) {
        context.putString(key, value)
    }

    fun put(key: String, value: Boolean) {
        context.putBool(key, value)
    }

    fun put(key: String, value: RetroUser?) {
        context.putObject(key, value)
    }

    fun getInt(key: String, default: Int): Int = context.getInt(key, default)

    fun getString(key: String): String? = context.getString(key)

    fun getBool(key: String, default: Boolean): Boolean =
        context.getBool(key, default)

    fun getObject(key: String, default: RetroUser?): RetroUser? =
        context.getObject(key, default)
}