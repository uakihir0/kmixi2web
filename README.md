> [日本語](./docs/README_ja.md)

# kmixi2web

![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo.repsy.io%2Fmvn%2Fuakihir0%2Fpublic%2Fwork%2Fsocialhub%2Fkmixi2web%2Fcore%2Fmaven-metadata.xml)

![badge][badge-js]
![badge][badge-jvm]
![badge][badge-ios]
![badge][badge-mac]

**This library is a mixi2 client library that supports [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html).**
It internally uses Ktor Client.
Therefore, this library is available on Kotlin Multiplatform and platforms supported by Ktor Client.

mixi2 publishes no official API, so this library speaks the undocumented
protobuf RPC interface used by the mixi2 web client. Method names, field
numbers, headers, and authentication behavior can change without notice. See
[protocol.md](./docs/protocol.md) for the observed wire format.

## Usage

Below is how to use it in Kotlin with Gradle on supported platforms.
**If you want to use it on Apple platforms, please refer to [CocoaPods](./docs/pods/README.md) or [Swift Package](./docs/spm/README.md).**
**Also, for usage in JavaScript, please refer to [JavaScript](./docs/js/README.md).**
Please refer to the test code for how to use each API.

```kotlin:build.gradle.kts
repositories {
    mavenCentral()
+   maven { url = uri("https://repo.repsy.io/mvn/uakihir0/public") }
}

dependencies {
+   implementation("work.socialhub.kmixi2web:core:0.1.0-SNAPSHOT")
}
```

### Using as part of a regular Java project

All of the above can be added to and used in regular Java projects, too. All you have to do is to use the suffix `-jvm` when listing the dependency.

Here is a sample Maven configuration:

```xml
<dependency>
    <groupId>work.socialhub.kmixi2web</groupId>
    <artifactId>core-jvm</artifactId>
    <version>[VERSION]</version>
</dependency>
```

### Authentication

mixi2 web authenticates with a browser cookie and an `x-auth-key` header. In
browser DevTools, inspect a request under
`/api/connect/com.mixi.mercury.api.MercuryService/` and copy both values.

Obtain the cookie and `x-auth-key` only from a mixi2 account you are authorized
to use. Treat both values as secrets. Do not commit them to source control.

```kotlin
val mixi2 = Mixi2WebFactory.instance(
    cookie = System.getenv("MIXI2_COOKIE"),
    authKey = System.getenv("MIXI2_AUTH_KEY"),
)

val session = mixi2.session().getSession().data
val active = session.sessionManagedPersonas
    .firstOrNull { it.profile?.persona?.personaId == session.activePersonaId }

println(active?.profile?.persona?.name)
```

One account can hold several personas. `session().switchPersona(...)` changes
the persona that subsequent calls act as.

### Get Timeline

```kotlin
val feeds = mixi2.timeline().getSubscribingFeeds(
    GetSubscribingFeedsRequest(limit = 50)
)

feeds.data.feeds.forEach { println(it.post?.text) }
```

A `Feed` entry carries either a `post` or a `communityAggregationPost`. Pass a
previous `nextCursor` back as `untilCursor` to page.

Recommended, following, persona, and hashtag timelines are available on the same
resource and return `posts` directly. `getReactionPosts(...)` reads back the
active persona's own liked or bookmarked posts.

### Create Post

```kotlin
mixi2.post().createPost(
    CreatePostRequest(text = "Hello from Kotlin Multiplatform")
)

mixi2.post().createPost(
    CreatePostRequest(text = "reply", inReplyToPostId = "POST_ID")
)

mixi2.post().createPost(
    CreatePostRequest(text = "comment", quotePostId = "POST_ID")
)
```

Bookmarks and reposts are edited through the same resource with
`createBookmark(...)`, `deleteBookmark(...)`, and `deleteRepost(...)`.
`deleteRepost(...)` takes the reposted post ID, not the repost's own ID.

### Reactions

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

`reaction().getStamps(...)` returns the available stamp catalog, including stamp
IDs and image URLs. `getLikingPersonas(...)` is restricted by the mixi2 service
to the post owner.

### Profile

```kotlin
val profile = mixi2.persona().getProfile(GetProfileRequest("PERSONA_ID")).data

println(profile.profile?.followingCount)
println(profile.profile?.isMuted)
```

`getProfileByName(...)` resolves the same `Profile` from a persona name, and
`updateProfile(...)` applies only the fields that are set.

### Follow Graph

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

### Notifications

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

### Moderation

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

### Search

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

Pass a previous `nextCursor` back as `untilCursor` to page.
`searchTypeahead(...)` returns persona suggestions for an incremental input.

### Media Upload

Media is uploaded before the post that references it. `uploadMedia(...)` runs
the whole sequence — prepare, upload the bytes to the returned presigned target,
and poll until processing finishes:

```kotlin
val uploaded = mixi2.media().uploadMedia(
    UploadMediaRequest(
        mimeType = "image/jpeg",
        data = imageBytes,
        category = MediaCategory.POST_IMAGE,
    )
).data

mixi2.post().createPost(
    CreatePostRequest(text = "with an image", mediaIds = listOf(uploaded.mediaId))
)
```

`prepareMediaUploading(...)` and `getMedia(...)` are also exposed for callers
that want to drive the upload themselves. Videos stay in
`MediaStatus.IN_PROGRESS` while the service transcodes them, so `pollAttempts`
and `pollIntervalMillis` bound how long `uploadMedia(...)` waits.

### Communities

Communities are mixi2's groups, and they back both topic and event communities:

```kotlin
val communities = mixi2.community().getParticipatingCommunities(
    GetParticipatingCommunitiesRequest(rejectArchived = true)
).data.communities

val timeline = mixi2.community().getCommunityTimeline(
    GetCommunityTimelineRequest(communityId = communities.first().communityId)
).data.posts
```

`joinCommunity(...)` works for `CommunityAccessLevel.PUBLIC` communities;
`APPROVAL_REQUIRED` ones need `requestJoinCommunity(...)` and an admin approval.

### Chat

Chat covers both direct and group conversations:

```kotlin
val rooms = mixi2.chat().getChatRooms(GetChatRoomsRequest(limit = 20)).data.rooms

val messages = mixi2.chat().getChatRoomMessages(
    GetChatRoomMessagesRequest(roomId = rooms.first().roomId, limit = 50)
).data.messages

mixi2.chat().sendDirectMessage(
    SendDirectMessageRequest(receiverId = "PERSONA_ID", text = "hello")
)
```

`sendDirectMessage(...)` creates the one-to-one room when it does not exist yet,
so the room ID has to be read back from the returned message. Use
`sendMessageToRoom(...)` for a room you already know and `sendGroupMessage(...)`
to open a group room with several personas.

### Browser JavaScript

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

### Raw RPC

`raw().call(rpcName, requestBody)` sends any protobuf payload to a
MercuryService unary method and returns the response bytes. This keeps the
client useful when the web interface adds methods before typed models are
available.

## Supported API

- Subscribing, recommended, following, personal, and hashtag timelines
- Single and bulk post lookup, replies, ancestors, and thread posts
- Text, reply, quote, repost, community, and media post creation and deletion
- Like counts, stamp reaction counts, stamp catalog, and stamp image URLs
- Like and stamp creation/removal, and per-post reaction persona lookup
- Notifications, activity filtering, unread badge counts, and read markers
- Session lookup for the authenticated account, its personas, and switching
- Persona and profile lookup by ID or name, and profile updates
- Following and follower lists with cursor paging, follow and unfollow
- Follow requests, and approving, rejecting, and listing pending requests
- Blocking, muting, their persona ID lists, and post/persona reporting
- Post and persona search, including typeahead suggestions
- Bookmark creation and removal, repost removal, and quote/repost persona lookup
- Liked and bookmarked post timelines
- Media upload preparation, binary upload, and processing status polling
- Participating community lists, community lookup, and community timelines
- Community member lists, joining, join requests, and leaving
- Chat room lists, single room lookup, room message history, and unread counts
- Direct, group, and existing-room message sending with media and shared posts
- Raw unary calls for MercuryService methods not yet exposed as typed resources

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

KMIXI2WEB_LIVE_MODE=graph-read \
  ./gradlew :core:jvmTest \
  --tests work.socialhub.kmixi2web.GraphLiveTest.readFollowingsAndFollowers

KMIXI2WEB_LIVE_MODE=search-read \
  ./gradlew :core:jvmTest \
  --tests work.socialhub.kmixi2web.GraphLiveTest.searchPersonasAndPosts

KMIXI2WEB_LIVE_MODE=community-read \
  ./gradlew :core:jvmTest \
  --tests work.socialhub.kmixi2web.CommunityLiveTest.readCommunitiesTimelineAndMembers

KMIXI2WEB_LIVE_MODE=chat-read \
  ./gradlew :core:jvmTest \
  --tests work.socialhub.kmixi2web.ChatLiveTest.readChatRoomsAndMessages
```

Every mode above except `reaction-write` is read-only. The read-only modes skip
themselves rather than fail when the account has nothing to read — no community,
no chat room, or no search hit. The write test requires a post you own without
an existing like or stamp from the active persona; it adds and then removes both
reactions and verifies that the original counts are restored.

The initial schema was validated against the mixi2 web client and the MIT
licensed `mixi2` npm package version 0.2.2.

## License

MIT License

## Author

[Akihiro Urushihara](https://github.com/uakihir0)

[badge-android]: http://img.shields.io/badge/-android-6EDB8D.svg
[badge-android-native]: http://img.shields.io/badge/support-[AndroidNative]-6EDB8D.svg
[badge-wearos]: http://img.shields.io/badge/-wearos-8ECDA0.svg
[badge-jvm]: http://img.shields.io/badge/-jvm-DB413D.svg
[badge-js]: http://img.shields.io/badge/-js-F8DB5D.svg
[badge-js-ir]: https://img.shields.io/badge/support-[IR]-AAC4E0.svg
[badge-nodejs]: https://img.shields.io/badge/-nodejs-68a063.svg
[badge-linux]: http://img.shields.io/badge/-linux-2D3F6C.svg
[badge-windows]: http://img.shields.io/badge/-windows-4D76CD.svg
[badge-wasm]: https://img.shields.io/badge/-wasm-624FE8.svg
[badge-apple-silicon]: http://img.shields.io/badge/support-[AppleSilicon]-43BBFF.svg
[badge-ios]: http://img.shields.io/badge/-ios-CDCDCD.svg
[badge-mac]: http://img.shields.io/badge/-macos-111111.svg
[badge-watchos]: http://img.shields.io/badge/-watchos-C0C0C0.svg
[badge-tvos]: http://img.shields.io/badge/-tvos-808080.svg
