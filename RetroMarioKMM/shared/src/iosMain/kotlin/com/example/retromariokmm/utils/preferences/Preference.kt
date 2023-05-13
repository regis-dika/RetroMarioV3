package com.example.retromariokmm.utils.preferences

import com.example.retromariokmm.domain.models.RetroUser
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults

actual fun KMMContext.putInt(key: String, value: Int) {
    NSUserDefaults.standardUserDefaults.setInteger(value.toLong(), key)
}

actual fun KMMContext.getInt(key: String, default: Int): Int {
    return NSUserDefaults.standardUserDefaults.integerForKey(key).toInt()
}

actual fun KMMContext.putString(key: String, value: String) {
    NSUserDefaults.standardUserDefaults.setObject(value, key)
}

actual fun KMMContext.getString(key: String): String? {
    return NSUserDefaults.standardUserDefaults.stringForKey(key)
}

actual fun KMMContext.putBool(key: String, value: Boolean) {
    NSUserDefaults.standardUserDefaults.setBool(value, key)
}

actual fun KMMContext.getBool(key: String, default: Boolean): Boolean {
    return NSUserDefaults.standardUserDefaults.boolForKey(key)
}

actual fun KMMContext.putObject(key: String, value: RetroUser?) {
    val json = Json.encodeToString(value)
    NSUserDefaults.standardUserDefaults.putString(key, json)
}

actual fun KMMContext.getObject(key: String, default: RetroUser?): RetroUser? {
    val json = NSUserDefaults.standardUserDefaults.getString("key")
    return json?.let<String, RetroUser> { Json.decodeFromString(it) }
}
