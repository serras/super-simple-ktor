package com.serranofp.ktor.simple

import arrow.fx.coroutines.ResourceScope
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.routing.route as ktorRoute

public data class SuperSimpleKtor @PublishedApi internal constructor(
  val routing: Route,
  val resource: ResourceScope
): ResourceScope by resource {
  public fun route(
    method: HttpMethod,
    route: String,
    body: suspend Handler.() -> Unit
  ): Route = routing.ktorRoute(route, method) { handle { call.body() } }

  public fun get(
    route: String,
    body: suspend Handler.() -> Unit
  ): Route = route(HttpMethod.Get, route, body)

  public fun post(
    route: String,
    body: suspend Handler.() -> Unit
  ): Route = route(HttpMethod.Post, route, body)

  public fun put(
    route: String,
    body: suspend Handler.() -> Unit
  ): Route = route(HttpMethod.Put, route, body)

  public fun patch(
    route: String,
    body: suspend Handler.() -> Unit
  ): Route = route(HttpMethod.Patch, route, body)

  public fun delete(
    route: String,
    body: suspend Handler.() -> Unit
  ): Route = route(HttpMethod.Delete, route, body)
}
