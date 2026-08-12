# kmixi2web

mixi2 Web が利用している非公式 protobuf RPC を Kotlin Multiplatform から
呼び出すためのライブラリです。

## 対応機能

- 購読、推薦、フォロー、ユーザー、ハッシュタグの各タイムライン
- 単一・複数ポストの取得
- リプライ、祖先ポスト、スレッドの取得
- テキスト投稿、返信、引用、リポスト、コミュニティ投稿、メディア付き投稿
- ポスト削除
- ID・ユーザー名によるペルソナ取得
- 未型定義 RPC を呼び出す raw unary API

## 認証

ログイン済み mixi2 Web の開発者ツールで、
`/api/connect/com.mixi.mercury.api.MercuryService/` 以下の通信を確認し、
自分が利用権限を持つアカウントの Cookie と `x-auth-key` を取得します。

これらはパスワードと同様の秘密情報です。ソースコードや Git へ保存しないでください。

```kotlin
val mixi2 = Mixi2WebFactory.instance(
    cookie = System.getenv("MIXI2_COOKIE"),
    authKey = System.getenv("MIXI2_AUTH_KEY"),
)

val feeds = mixi2.timeline().getSubscribingFeeds(
    GetSubscribingFeedsRequest(limit = 50)
)

val post = mixi2.post().createPost(
    CreatePostRequest(text = "Kotlin Multiplatform から投稿")
)
```

返信は `inReplyToPostId`、引用は `quotePostId` を指定します。

## JavaScript ブラウザ

ブラウザはクロスオリジンの `Cookie` ヘッダーを直接設定できません。
同一オリジンのプロキシで `x-proxy-cookie` を `Cookie` へ変換し、
`Mixi2WebFactory.instanceForProxy(...)` を使用してください。

プロキシは必ず認証し、不特定の利用者が任意の Cookie を転送できないようにしてください。

## 注意

mixi2 の非公開 Web インターフェースを利用するため、予告なく動作しなくなる可能性があります。
利用規約と適用法令を確認し、自分が操作権限を持つアカウントだけで利用してください。
