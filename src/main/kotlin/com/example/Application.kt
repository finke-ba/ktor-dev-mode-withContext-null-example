package com.example

import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val applicationConfig = HoconApplicationConfig(ConfigFactory.load())

    val portProperty = applicationConfig.property("ktor.deployment.port").getString().toInt()
    val devModeProperty = applicationConfig.property("ktor.development").getString().toBoolean()

    val environment = applicationEngineEnvironment {
        developmentMode = devModeProperty
        connector {
            port = portProperty
        }

        module { serverConfigModule() }
        module { firstModule() }
//      CustomCoroutineContext == null if developmentMode = true, if developmentMode = false then CustomCoroutineContext == contextValue
        module { secondModule(portProperty) }
//      CustomCoroutineContext == contextValue if developmentMode = true, if developmentMode = false then CustomCoroutineContext == contextValue
        module { thirdModule(8080) }
    }

    embeddedServer(Netty, environment, configure = {}).start(wait = true)
}

fun Application.serverConfigModule() {
    install(CustomCoroutineContextFeature)
}

fun Application.firstModule() {
    routing {
        get("/first") {
            val contextValue = coroutineContext[CustomCoroutineContext]?.contextValue

            call.respondText(contextValue!!)
        }
    }
}

fun Application.secondModule(port: Int) {
    routing {
        get("/second") {
            val contextValue = coroutineContext[CustomCoroutineContext]?.contextValue

            call.respondText(contextValue!!)
        }
    }
}

fun Application.thirdModule(port: Int) {
    routing {
        get("/third") {
            val contextValue = coroutineContext[CustomCoroutineContext]?.contextValue

            call.respondText(contextValue!!)
        }
    }
}
