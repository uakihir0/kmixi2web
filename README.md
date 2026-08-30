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
- Like counts, stamp reaction counts, and stamp image URLs
- Stamp catalog and per-post stamp reaction lookup
- Like and stamp creation/removal
- Notifications, activity filtering, and unread badge counts
- Individual and time-based notification read markers
- Persona lookup by ID or name
- Session lookup for the authenticated account and its managed personas
- Persona switching
- Profile lookup by persona ID or name, including follow counts and moderation state
- Profile updates for display name, profile text, status, and link
- Following and follower lists with cursor paging
- Follow and unfollow, plus follow requests for protected personas
- Approving, rejecting, and listing pending follow requests
- Blocking, muting, and their blocked/muted persona ID lists
- Reporting posts and personas with a reason type
- Post and persona search, including typeahead suggestions
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

Reaction summaries are included in each `Post`:

```kotlin
val post = mixi2.post().getPost(GetPostRequest("POST_ID")).data.post!!

println(post.likesCount)
post.stamps.forEach { summary ->
    println("${summary.stamp?.url}: ${summary.count}")
}

val reactions = mixi2.reaction().getPostStampReactions(
    GetPostStampReactionsRequest(post.postId, limit = 100)
)
```

`reaction().getStamps(...)` returns the available stamp catalog, including
stamp IDs and image URLs. `getLikingPersonas(...)` is restricted by the mixi2
service to the post owner.

The authenticated account and its personas come from the session:

```kotlin
val session = mixi2.session().getSession().data
val active = session.sessionManagedPersonas
    .firstOrNull { it.profile?.persona?.personaId == session.activePersonaId }

println(active?.profile?.persona?.name)
println(active?.profile?.followingCount)
```

`persona().getProfile(...)` and `persona().getProfileByName(...)` return the same
`Profile` for any persona, including follow counts, `isMuted`, `isBlocking`, and
`personaConnectivity`. `persona().updateProfile(...)` applies only the fields
that are set.

The follow graph is paged with an opaque cursor:

```kotlin
val followings = mixi2.follow().getFollowings(
    GetFollowingsRequest(personaId = "PERSONA_ID", limit = 50)
).data

followings.followings.forEach { println(it.persona?.name) }

val next = mixi2.follow().getFollowings(
    GetFollowingsRequest(personaId = "PERSONA_ID", cursorId = followings.cursorId)
)
```

`createFollowing(...)` and `deleteFollowing(...)` change the follow state
directly. A persona that approves followers manually needs
`sendFollowingRequest(...)` instead, and the receiving side uses
`getPendingFollowingRequests(...)` with `approveFollowingRequest(...)` or
`rejectFollowingRequest(...)`.

Blocking, muting, and reporting live on `moderation()`:

```kotlin
mixi2.moderation().blockPersona(MakePersonaBlockRequest("PERSONA_ID"))
mixi2.moderation().mutePersona(MakePersonaMuteRequest("PERSONA_ID"))

val blocked = mixi2.moderation().getBlockPersonas().data.personaIds

mixi2.moderation().reportPost(
    ReportPostRequest(
        postId = "POST_ID",
        reasonType = ReportReasonType.SPAM,
        reasonContent = "reason",
    )
)
```

`getBlockPersonas()` and `getMutePersonas()` return persona IDs only; resolve
them with `persona().getPersonas(...)`. `blockPersona(...)` returns the updated
`Profile`, so `isBlocking` can be read back without a second call.

One search call can carry several independent operations, each identified by an
`operationId` that the matching result repeats:

```kotlin
val results = mixi2.search().search(
    SearchRequest(
        query = "kotlin",
        operations = listOf(
            SearchOperation(type = SearchType.POSTS, operationId = 1, limit = 20),
            SearchOperation(type = SearchType.PERSONAS, operationId = 2, limit = 20),
        ),
    )
).data

val posts = results.results.first { it.operationId == 1 }.postsResult
val personas = results.results.first { it.operationId == 2 }.personasResult

println(posts?.nextCursor)
```

Pass a previous `nextCursor` back as `untilCursor` to page. `searchTypeahead(...)`
returns persona suggestions for an incremental input.

Notifications can be paged and filtered by activity type:

```kotlin
val badge = mixi2.notification().getBadgeCount()
println(badge.data.personaUnreadNotificationCount)

val notifications = mixi2.notification().getNotifications(
    GetNotificationsRequest(
        limit = 50,
        activityTypes = listOf(
            NotificationActivityType.REPLY,
            NotificationActivityType.MENTION,
            NotificationActivityType.REACTION,
        ),
    )
)

notifications.data.notifications.forEach {
    println("${it.activityType}: ${it.postId}")
    println(it.reaction?.imageUrl)
}
```

`markNotificationAsRead(...)` and `markNotificationsAsReadBeforeTime(...)`
change the server-side read state.

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

Authenticated live tests read `secrets.json` from the repository root. Copy
`secrets.json.default`, fill in your own cookie and auth key, and keep the file
untracked.

```shell
KMIXI2WEB_LIVE_MODE=reaction-read \
  ./gradlew :core:jvmTest \
  --tests work.socialhub.kmixi2web.ReactionLiveTest.readReactionCountsAndImageUrls

KMIXI2WEB_LIVE_MODE=reaction-write \
KMIXI2WEB_POST_ID=YOUR_OWN_POST_ID \
  ./gradlew :core:jvmTest \
  --tests work.socialhub.kmixi2web.ReactionLiveTest.controlledReactionRoundTrip

KMIXI2WEB_LIVE_MODE=notification-read \
  ./gradlew :core:jvmTest \
  --tests work.socialhub.kmixi2web.NotificationLiveTest.readNotificationsAndBadgeCounts
```

The write test requires a post you own without an existing like or stamp from
the active persona. It adds and then removes both reactions and verifies that
the original counts are restored.

## License

MIT
