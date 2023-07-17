package com.serranofp.ktor.simple

import arrow.continuations.SuspendApp
import arrow.continuations.ktor.server
import arrow.fx.coroutines.ResourceScope
import arrow.fx.coroutines.resourceScope
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.routing.*
import kotlinx.coroutines.awaitCancellation

public fun superSimpleKtor(
  port: Int = 8080,
  host: String = "0.0.0.0",
  additionalConfiguration: Application.() -> Unit = { },
  routes: SuperSimpleKtor.() -> Unit
): Unit = SuspendApp {
  resourceScope {
    server(CIO, port = port, host = host) {
      superSimpleKtorApp(this, additionalConfiguration, routes)
    }
    awaitCancellation()
  }
}

public inline fun ResourceScope.superSimpleKtorApp(
  application: Application,
  additionalConfiguration: Application.() -> Unit = { },
  crossinline routes: SuperSimpleKtor.() -> Unit
) {
  with(application) {
    additionalConfiguration()
    install(ContentNegotiation) {
      json()
    }
    install(AutoHeadResponse)
    install(DoubleReceive)
    routing {
      routes(SuperSimpleKtor(this, this@superSimpleKtorApp))
    }
  }
}
