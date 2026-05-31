package com.example

import io.ktor.server.netty.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        module()
    }.start(wait = true)
}