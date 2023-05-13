package com.example.retromariokmm.utils.preferences

import com.example.retromariokmm.domain.models.Retro
import com.example.retromariokmm.domain.models.RetroUser

expect fun KMMContext.putInt(key: String, value: Int)

expect fun KMMContext.getInt(key: String, default: Int): Int

expect fun KMMContext.putString(key: String, value: String)

expect fun KMMContext.getString(key: String) : String?

expect fun KMMContext.putBool(key: String, value: Boolean)

expect fun KMMContext.getBool(key: String, default: Boolean): Boolean

expect fun KMMContext.putObject(key: String, value: RetroUser?)

expect fun KMMContext.getObject(key: String, default: RetroUser?): RetroUser?