package org.generadorfc.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform