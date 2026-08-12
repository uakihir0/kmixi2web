> [日本語](./docs/README_ja.md)

# kmixi2web

Kotlin Multiplatform client for the unofficial mixi2 web protobuf RPC interface.

The library follows the resource/factory structure used by the sibling `kxxxx`
projects and supports JVM, JavaScript, iOS, and macOS.

## Supported API

- Subscribing, recommended, following, personal, and hashtag timelines
- Single and bulk post lookup
- Replies, ancestors, and thread posts
- Text, reply, quote, repost, community, and media post creation
- Post deletion
- Persona lookup by ID or name
- Raw unary calls for MercuryService methods not yet exposed as typed resources

## Authentication

mixi2 web sends unary protobuf requests to:

```text
https://mixi.social/api/connect/com.mixi.mercury.api.MercuryService/{RpcName}
```

Requests use `application/proto` with these headers:

- `x-auth-key`
- `x-mercury-user-agent`
- the authenticated browser cookie

Obtain the cookie and `x-auth-key` only from a mixi2 account you are authorized
to use. In browser DevTools, inspect a request under
`/api/connect/com.mixi.mercury.api.MercuryService/`.

Treat both values as secrets. Do not commit them to source control.

## Kotlin Usage

```kotlin
val mixi2 = Mixi2WebFactory.instance(
    cookie = System.getenv("MIXI2_COOKIE"),
    authKey = System.getenv("MIXI2_AUTH_KEY"),
)

val timeline = mixi2.timeline().getSubscribingFeeds(
    GetSubscribingFeedsRequest(limit = 50)
)

val created = mixi2.post().createPost(
    CreatePostRequest(text = "Hello from Kotlin Multiplatform")
)
```

Reply and quote creation use the same RPC:

```kotlin
CreatePostRequest(
    text = "reply",
    inReplyToPostId = "POST_ID",
)

CreatePostRequest(
    text = "comment",
    quotePostId = "POST_ID",
)
```

## Browser JavaScript

Browsers cannot set a cross-origin `Cookie` header. Put a same-origin proxy in
front of the official endpoint and forward `x-proxy-cookie` as `Cookie`, then:

```kotlin
val mixi2 = Mixi2WebFactory.instanceForProxy(
    cookie = cookie,
    authKey = authKey,
    baseUrl = "https://your-proxy.example/MercuryService",
)
```

Never expose a proxy that accepts arbitrary cookies from untrusted callers.

## Raw RPC

`raw().call(rpcName, requestBody)` sends any protobuf payload to a MercuryService
unary method and returns the response bytes. This keeps the client useful when
the web interface adds methods before typed models are available.

## Stability

This project uses an undocumented web interface. Method names, field numbers,
headers, and authentication behavior can change without notice.

The initial schema was validated against the mixi2 web client and the MIT
licensed `mixi2` npm package version 0.2.2.

## Verification

```shell
./gradlew :core:jvmTest
./gradlew :core:compileKotlinJs
```

## License

MIT
