# kmixi2web

![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo.repsy.io%2Fmvn%2Fuakihir0%2Fpublic%2Fwork%2Fsocialhub%2Fkmixi2web%2Fcore%2Fmaven-metadata.xml)

![badge][badge-js]
![badge][badge-jvm]
![badge][badge-ios]
![badge][badge-mac]

**このライブラリは [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) に対応した mixi2 クライアントライブラリです。**
内部で Ktor Client を使用しています。
そのため、本ライブラリは、Kotlin Multiplatform かつ Ktor Client がサポートしているプラットフォームであれば利用可能です。

mixi2 は公式 API を公開していないため、本ライブラリは mixi2 Web クライアントが
使用している非公開の protobuf RPC を呼び出します。メソッド名、フィールド番号、
ヘッダー、認証方式は予告なく変更される可能性があります。確認できた通信仕様は
[protocol.md](./protocol.md) にまとめています。

## 使い方

以下は対応するプラットフォームにおいて Gradle を用いて Kotlin で使用する際の使い方になります。
**Apple プラットフォームで使用する場合は、[CocoaPods](./pods/README_ja.md) または [Swift Package](./spm/README_ja.md) を参照してください。**
**また、JavaScript での使い方については、[JavaScript](./js/README_ja.md) を参照してください。**
テストコードも合わせて確認してください。

```kotlin:build.gradle.kts
repositories {
    mavenCentral()
+   maven { url = uri("https://repo.repsy.io/mvn/uakihir0/public") }
}

dependencies {
+   implementation("work.socialhub.kmixi2web:core:0.1.0-SNAPSHOT")
}
```

### 通常の Java プロジェクトで使用する場合

上記はすべて通常の Java プロジェクトにも追加して使用できます。依存関係にサフィックス `-jvm` を付けるだけです。

Maven の設定例:

```xml
<dependency>
    <groupId>work.socialhub.kmixi2web</groupId>
    <artifactId>core-jvm</artifactId>
    <version>[VERSION]</version>
</dependency>
```

### 認証

mixi2 Web は、ブラウザの Cookie と `x-auth-key` ヘッダーで認証します。
ログイン済み mixi2 Web の開発者ツールで
`/api/connect/com.mixi.mercury.api.MercuryService/` 以下の通信を確認し、
両方の値を取得します。

Cookie と `x-auth-key` は、自分が利用権限を持つアカウントからのみ取得してください。
これらはパスワードと同様の秘密情報です。ソースコードや Git へ保存しないでください。

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

1 つのアカウントは複数のペルソナを持てます。以降の呼び出しで使用するペルソナは
`session().switchPersona(...)` で切り替えます。

### タイムライン取得

```kotlin
val feeds = mixi2.timeline().getSubscribingFeeds(
    GetSubscribingFeedsRequest(limit = 50)
)

feeds.data.feeds.forEach { println(it.post?.text) }
```

`Feed` は `post` または `communityAggregationPost` のいずれかを保持します。
続きを取得する場合は、取得済みの `nextCursor` を `untilCursor` に指定します。

推薦、フォロー、ユーザー、ハッシュタグの各タイムラインも同じリソースから
取得でき、こちらは `posts` を直接返します。`getReactionPosts(...)` は自分が
いいね・ブックマークしたポストの一覧を返します。

### ポスト投稿

```kotlin
mixi2.post().createPost(
    CreatePostRequest(text = "Kotlin Multiplatform から投稿")
)

mixi2.post().createPost(
    CreatePostRequest(text = "返信", inReplyToPostId = "POST_ID")
)

mixi2.post().createPost(
    CreatePostRequest(text = "引用", quotePostId = "POST_ID")
)
```

ブックマークとリポストの取り消しも同じリソースの `createBookmark(...)`、
`deleteBookmark(...)`、`deleteRepost(...)` から操作します。
`deleteRepost(...)` にはリポスト自身の ID ではなく、リポスト元のポスト ID を
指定します。

### リアクション

各 `Post` にはリアクション集計が含まれます。

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

`reaction().getStamps(...)` では、stamp ID と画像 URL を含む利用可能な
stamp カタログを取得できます。`getLikingPersonas(...)` は mixi2 側の制約により、
そのポストの投稿者だけが呼び出せます。

### プロフィール

```kotlin
val profile = mixi2.persona().getProfile(GetProfileRequest("PERSONA_ID")).data

println(profile.profile?.followingCount)
println(profile.profile?.isMuted)
```

`getProfileByName(...)` はペルソナ名から同じ `Profile` を取得します。
`updateProfile(...)` は値が設定されたフィールドのみを更新します。

### フォローグラフ

フォローグラフは `cursorId` によるページングで取得します。

```kotlin
val followings = mixi2.follow().getFollowings(
    GetFollowingsRequest(personaId = "PERSONA_ID", limit = 50)
).data

followings.followings.forEach { println(it.persona?.name) }

val next = mixi2.follow().getFollowings(
    GetFollowingsRequest(personaId = "PERSONA_ID", cursorId = followings.cursorId)
)
```

`createFollowing(...)` と `deleteFollowing(...)` はフォロー状態を直接変更します。
承認制のペルソナには `sendFollowingRequest(...)` を使用し、
受信側は `getPendingFollowingRequests(...)` で一覧を取得して
`approveFollowingRequest(...)` または `rejectFollowingRequest(...)` を呼び出します。

### 通知

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

`markNotificationAsRead(...)` と `markNotificationsAsReadBeforeTime(...)` は
サーバー側の既読状態を変更します。

### ブロック・ミュート・通報

```kotlin
mixi2.moderation().blockPersona(MakePersonaBlockRequest("PERSONA_ID"))
mixi2.moderation().mutePersona(MakePersonaMuteRequest("PERSONA_ID"))

val blocked = mixi2.moderation().getBlockPersonas().data.personaIds

mixi2.moderation().reportPost(
    ReportPostRequest(
        postId = "POST_ID",
        reasonType = ReportReasonType.SPAM,
        reasonContent = "通報理由",
    )
)
```

`getBlockPersonas()` と `getMutePersonas()` はペルソナ ID のみを返すため、
必要に応じて `persona().getPersonas(...)` で解決します。
`blockPersona(...)` は更新後の `Profile` を返すので、`isBlocking` を
再取得せずに確認できます。

### 検索

検索は 1 回のリクエストに複数の operation を含められます。
結果は `operationId` で対応付けます。

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
```

続きを取得する場合は、取得済みの `nextCursor` を `untilCursor` に指定します。
`searchTypeahead(...)` は入力途中のペルソナ候補を返します。

### メディアアップロード

メディアは投稿より先にアップロードします。`uploadMedia(...)` は準備、
presigned URL へのバイナリ送信、処理完了までのポーリングをまとめて実行します。

```kotlin
val uploaded = mixi2.media().uploadMedia(
    UploadMediaRequest(
        mimeType = "image/jpeg",
        data = imageBytes,
        category = MediaCategory.POST_IMAGE,
    )
).data

mixi2.post().createPost(
    CreatePostRequest(text = "画像付き投稿", mediaIds = listOf(uploaded.mediaId))
)
```

自分でアップロード手順を制御する場合は `prepareMediaUploading(...)` と
`getMedia(...)` を直接使用します。動画は変換中 `MediaStatus.IN_PROGRESS` の
ままになるため、待機時間は `pollAttempts` と `pollIntervalMillis` で調整します。

### コミュニティ

コミュニティは mixi2 のグループ機能で、トピックコミュニティとイベント
コミュニティの両方を扱います。

```kotlin
val communities = mixi2.community().getParticipatingCommunities(
    GetParticipatingCommunitiesRequest(rejectArchived = true)
).data.communities

val timeline = mixi2.community().getCommunityTimeline(
    GetCommunityTimelineRequest(communityId = communities.first().communityId)
).data.posts
```

`joinCommunity(...)` は `CommunityAccessLevel.PUBLIC` のコミュニティで使用します。
`APPROVAL_REQUIRED` の場合は `requestJoinCommunity(...)` で申請し、管理者の承認が
必要です。

### チャット

チャットは 1 対 1 とグループの両方に対応しています。

```kotlin
val rooms = mixi2.chat().getChatRooms(GetChatRoomsRequest(limit = 20)).data.rooms

val messages = mixi2.chat().getChatRoomMessages(
    GetChatRoomMessagesRequest(roomId = rooms.first().roomId, limit = 50)
).data.messages

mixi2.chat().sendDirectMessage(
    SendDirectMessageRequest(receiverId = "PERSONA_ID", text = "hello")
)
```

`sendDirectMessage(...)` はルームが存在しない場合に新規作成するため、ルーム ID は
戻り値のメッセージから取得します。既知のルームには `sendMessageToRoom(...)`、
複数の相手とグループルームを作成する場合は `sendGroupMessage(...)` を使用します。

### JavaScript ブラウザ

ブラウザはクロスオリジンの `Cookie` ヘッダーを直接設定できません。
同一オリジンのプロキシで `x-proxy-cookie` を `Cookie` へ変換してから、
以下のように接続します。

```kotlin
val mixi2 = Mixi2WebFactory.instanceForProxy(
    cookie = cookie,
    authKey = authKey,
    baseUrl = "https://your-proxy.example/MercuryService",
)
```

プロキシは必ず認証し、不特定の利用者が任意の Cookie を転送できないようにしてください。

### Raw RPC

`raw().call(rpcName, requestBody)` は、任意の protobuf ペイロードを
MercuryService の unary メソッドへ送信し、レスポンスのバイト列を返します。
型定義が追加される前の新しいメソッドも、この API から呼び出せます。

## 対応機能

- 購読、推薦、フォロー、ユーザー、ハッシュタグの各タイムライン
- 単一・複数ポスト、リプライ、祖先ポスト、スレッドの取得
- テキスト投稿、返信、引用、リポスト、コミュニティ投稿、メディア付き投稿と削除
- いいね数、stamp リアクション数、stamp カタログ、stamp 画像 URL の取得
- いいね・stamp の追加と解除、ポストごとの反応者一覧
- 通知一覧、種類絞り込み、未読バッジ数、既読操作
- 認証済みアカウントのセッションと管理ペルソナ一覧の取得、ペルソナの切り替え
- ID・名前によるペルソナとプロフィールの取得、プロフィールの更新
- フォロー中・フォロワー一覧の取得 (カーソルページング)、フォロー・フォロー解除
- フォローリクエストの送信、受信一覧の取得と承認・拒否
- ブロック・ミュートとその ID 一覧の取得、ポスト・ペルソナの通報
- ポスト・ペルソナ検索とタイプアヘッド候補の取得
- ブックマークの追加・削除、リポストの取り消し、引用・リポストしたペルソナの取得
- いいね・ブックマークしたポストの一覧取得
- メディアのアップロード準備・バイナリ送信・処理状態のポーリング
- 参加コミュニティ一覧・コミュニティ取得・コミュニティタイムライン
- コミュニティメンバー一覧・参加・参加申請・退会
- チャットルーム一覧・ルーム取得・メッセージ履歴・未読ルーム数の取得
- ダイレクト・グループ・既存ルームへのメッセージ送信 (メディア・ポスト共有対応)
- 未型定義 RPC を呼び出す raw unary API

## 動作確認

```shell
./gradlew :core:jvmTest
./gradlew :core:compileKotlinJs
```

認証が必要なライブテストは、リポジトリ直下の `secrets.json` を読み込みます。
`secrets.json.default` をコピーし、自分の Cookie と `x-auth-key` を設定して
ください。このファイルは Git の管理対象外です。

```shell
KMIXI2WEB_LIVE_MODE=reaction-read \
  ./gradlew :core:jvmTest \
  --tests work.socialhub.kmixi2web.ReactionLiveTest.readReactionCountsAndImageUrls

KMIXI2WEB_LIVE_MODE=reaction-write \
KMIXI2WEB_POST_ID=自分のポストID \
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

`reaction-write` 以外のモードはすべて読み取り専用です。コミュニティ・チャット
ルーム・検索結果が存在しない場合は失敗せずにスキップします。書き込みテストには、
実行中のペルソナがまだいいね・stamp を付けていない自分のポストを指定します。
両方を追加して取得結果を確認した後に解除し、元の件数へ戻ったことまで検証します。

初期スキーマは mixi2 Web クライアントと、MIT ライセンスの `mixi2` npm パッケージ
バージョン 0.2.2 を参照して確認しています。

## ライセンス

MIT License

## 作者

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
