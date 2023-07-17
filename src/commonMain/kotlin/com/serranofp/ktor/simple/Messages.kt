package com.serranofp.ktor.simple

import io.ktor.server.html.respondHtml as ktorRespondHtml
import io.ktor.http.*
import io.ktor.http.ContentType as KtorContentType
import io.ktor.http.HttpStatusCode as KtorHttpStatusCode
import io.ktor.server.application.*
import kotlinx.html.HTML
import io.ktor.server.request.receive as ktorReceive
import io.ktor.server.request.receiveText as ktorReceiveText
import io.ktor.server.response.respond as ktorRespond
import io.ktor.server.response.respondText as ktorRespondText

public typealias Handler = ApplicationCall

public suspend inline fun <reified T : Any> Handler.receive(): T =
  ktorReceive<T>()

public suspend inline fun Handler.receiveText(): String =
  ktorReceiveText()

public typealias ContentType = KtorContentType
public typealias HttpStatusCode = KtorHttpStatusCode

public suspend inline fun <reified T : Any> Handler.respond(
  message: T,
  status: HttpStatusCode? = null
): Unit = when (status) {
  null -> ktorRespond(message)
  else -> ktorRespond(status, message)
}

public suspend fun Handler.respondText(
  text: String,
  contentType: ContentType? = null,
  status: HttpStatusCode? = null
): Unit = ktorRespondText(text, contentType, status)

public suspend fun Handler.respondHtml(
  status: HttpStatusCode? = null,
  block: HTML.() -> Unit
): Unit = when (status) {
  null -> ktorRespondHtml { block() }
  else -> ktorRespondHtml(status, block)
}
