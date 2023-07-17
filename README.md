# Module SuperSimpleKtor

The goal of this library is bring a Ktor server up in no-time,
using some good defaults. It is **not** intended for production
applications (just use Ktor then), but it becomes really helpful
as an educational tool.

```kotlin
import com.serranofp.ktor.simple.*

fun main() = superSimpleKtor {
    // define routes inside
    get("/hi/{name}") {
        respondText("Hello, ${parameters["name"]}!")
    }
}
```

## Main changes w.r.t. Ktor

- The [`Handler`](https://ktor.io/docs/requests.html) object
  is exposed directly (as receiver) within the route handlers,
  instead of having to use `call` to access it.
  - That means that `parameters` or `uri` are directly available.
- The routing in `superSimpleKtor` is restricted to the most basic
  options -- just HTTP method + route as string + action.
- The block in `superSimpleKtor` gets a
  [`ResourceScope`](https://arrow-kt.io/learn/coroutines/resource-safety/)
  in context, so you can manage resources correctly.

## Defaults

- The whole application runs within a
  [`SuspendApp`](https://arrow-kt.io/ecosystem/suspendapp/ktor/)
  context.
- [Content Negotiation](https://ktor.io/docs/serialization.html)
  is configured with JSON.
- [`DoubleReceive`](https://ktor.io/docs/double-receive.html)
  and [`AutoHeadResponse`](https://ktor.io/docs/autoheadresponse.html)
  plug-ins are applied.
