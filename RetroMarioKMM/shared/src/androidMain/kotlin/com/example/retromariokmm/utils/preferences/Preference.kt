package com.example.retromariokmm.utils.preferences

import com.example.retromariokmm.domain.models.RetroUser
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val SP_NAME = "kmm_app"

actual fun KMMContext.putInt(key: String, value: Int) {
    getSpEditor().putInt(key, value).apply()
}

actual fun KMMContext.getInt(key: String, default: Int): Int {
    return  getSp().getInt(key, default )
}

actual fun KMMContext.putString(key: String, value: String) {
    getSpEditor().putString(key, value).apply()
}

actual fun KMMContext.getString(key: String): String? {
    return  getSp().getString(key, null)
}

actual fun KMMContext.putBool(key: String, value: Boolean) {
    getSpEditor().putBoolean(key, value).apply()
}

actual fun KMMContext.getBool(key: String, default: Boolean): Boolean {
    return getSp().getBoolean(key, default)
}

actual fun KMMContext.putObject(key: String, value: RetroUser?) {
    val json = Json.encodeToString(value)
    getSpEditor().putString(key, json).apply()
}

actual fun KMMContext.getObject(key: String, default: RetroUser?): RetroUser? {
    val json = getSp().getString(key, null)
    //TODO refacto to handle the json
   return if (json != null && json != "null" && json.isNotBlank()){
        json.let<String, RetroUser> { Json.decodeFromString(it) }
    }else{
        null
    }
}

private fun KMMContext.getSp() = getSharedPreferences(SP_NAME, 0)

private fun KMMContext.getSpEditor() = getSp().edit()
