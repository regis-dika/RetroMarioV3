package com.example.retromariokmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform