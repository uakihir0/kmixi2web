# mixi2 Web protobuf RPC analysis

Analyzed on 2026-08-12.

## Transport

mixi2 Web uses unary protobuf-over-HTTP calls rather than a framed streaming
gRPC transport.

```text
POST https://mixi.social/api/connect/com.mixi.mercury.api.MercuryService/{RpcName}
Content-Type: application/proto
x-auth-key: {persona authentication key}
x-mercury-user-agent: Mercury-web/3.2.0
Cookie: {authenticated mixi2 browser cookie}
```

The request body is the protobuf message bytes. A successful response body is
the protobuf response message bytes. There is no five-byte gRPC-Web frame in
the observed client behavior.

Browser JavaScript cannot set `Cookie` for a cross-origin request. Proxy mode
therefore sends the same value as `x-proxy-cookie`; a trusted same-origin proxy
must translate that header to `Cookie`.

## Typed RPCs

### Timeline

| RPC | Request | Response |
| --- | --- | --- |
| `GetSubscribingFeeds` | `GetSubscribingFeedsRequest` | `GetSubscribingFeedsResponse` |
| `GetRecommendedTimeline` | `GetRecommendedTimelineRequest` | `GetTimelineResponse` |
| `GetFollowingsTimeline` | `GetFollowingsTimelineRequest` | `GetTimelineResponse` |
| `GetPersonalTimeline` | `GetPersonalTimelineRequest` | `GetTimelineResponse` |
| `GetHashtagTimeline` | `GetHashtagTimelineRequest` | `GetTimelineResponse` |

`GetSubscribingFeedsRequest`:

| Field | Number | Type |
| --- | ---: | --- |
| `until_cursor` | 1 | optional string |
| `limit` | 2 | optional uint32 |
| `since_cursor` | 3 | optional string |
| `end_cursor` | 4 | optional string |
| `feed_source_type` | 5 | optional enum |

The four general timeline cursors use `until_cursor_id`, `since_cursor_id`,
`limit`, and `end_cursor_id`. Personal timelines add `persona_id` at field 1;
hashtag timelines add `hashtag` at field 1 and `media_only` at field 2.

### Posts

| RPC | Request | Response |
| --- | --- | --- |
| `GetPost` | post ID | one post |
| `GetPosts` | repeated post IDs | repeated posts |
| `GetReplies` | post ID, limit, cursor | replies and next cursor |
| `GetReplyAncestors` | post ID, limit | ancestors and base post |
| `GetThreadPosts` | thread post ID and cursors | repeated posts |
| `CreatePost` | post composition | created post and pending flag |
| `DeletePost` | post ID | deleted flag |

`CreatePostRequest`:

| Field | Number | Type |
| --- | ---: | --- |
| `text` | 1 | string |
| `in_reply_to_post_id` | 2 | optional string |
| `quote_post_id` | 3 | optional string |
| `media_ids` | 4 | repeated string |
| `repost_id` | 5 | optional string |
| `is_sensitive` | 6 | bool |
| `community_id` | 7 | optional string |
| `attached_community_id` | 8 | optional string |
| `decorations` | 9 | repeated uint32 |
| `mask_type` | 10 | optional enum |
| `mask_caption` | 11 | optional string |
| `publishing_type` | 12 | optional enum |

`Post` currently has fields 1 through 34. The library models the stable reading
surface including IDs, timestamps, text, counts, media, reply/repost/quote
references, reactions, links, mentions, decorations, and mask state. Unknown
fields are skipped by protobuf decoding.

### Reactions

| RPC | Request | Response |
| --- | --- | --- |
| `GetPostStampReactions` | post ID, cursor, limit | stamp summaries, reacting personas, next cursor |
| `GetStamps` | language, repeated community IDs | official, community, and obtained stamp sets |
| `AddStampToPost` | post ID, stamp ID | updated post |
| `RemoveStampFromPost` | post ID, stamp ID | updated post |
| `CreateLike` | post ID | updated post |
| `DeleteLike` | post ID | updated post |
| `GetLikingPersonas` | post ID, limit, cursor | personas, next cursor, has-next flag |

`Post.stamps` is field 29. Each entry contains a stamp ID, image URL, and
reaction count. `Post.readerStampId` is field 30 and identifies the active
persona's stamp reaction. `Post.likesCount` and `Post.liked` are fields 9 and
16.

`GetPostStampReactions` can be used to paginate the personas behind each stamp
summary. `GetLikingPersonas` is accepted only when the active persona owns the
post; non-owners receive an owner-only service error.

### Notifications

| RPC | Request | Response |
| --- | --- | --- |
| `GetNotifications` | activity types, limit, time-series cursors | notifications and has-next flag |
| `GetBadgeCount` | empty | account, notification, and chat unread counts |
| `MarkNotificationAsRead` | notification time-series ID | empty |
| `MarkNotificationsAsReadBeforeTime` | latest time-series ID | empty |

Each notification contains an activity type, timestamp, time-series ID, and
issuer ID. Depending on its type it can also include a post ID, community ID,
community or following request ID, and stamp reaction details with an image
URL.

Notification activity values are non-contiguous protobuf enum numbers:
follow-related values use the 100 range, post activity uses the 200 range,
community activity uses the 300 range, and event activity uses the 400 range.
The client uses a custom serializer so values such as reply `204`, mention
`205`, and reaction `206` are not encoded as Kotlin enum ordinals.

### Personas

| RPC | Request | Response |
| --- | --- | --- |
| `GetPersonas` | repeated persona IDs | repeated personas |
| `GetPersonaByName` | persona name | one persona |

Timeline posts contain `persona_id`; clients can batch those IDs through
`GetPersonas`.

## Other observed MercuryService groups

The service also exposes authentication/session, follows, bookmarks,
communities/events, chat, media upload preparation, search, profile,
moderation, and remote-settings RPCs. They can be called with `RawResource`
immediately and should be promoted to typed resources only after their field
numbers are covered by offline wire tests.

Representative method names:

- Session: `Signin`, `SignOut`, `GetSession`, `RefreshToken`, `SwitchPersona`
- Engagement: `CreateBookmark`, `DeleteBookmark`
- Social graph: `GetFollowers`, `GetFollowings`, `CreateFollowing`,
  `DeleteFollowing`
- Communities: `GetCommunity`, `GetCommunityTimeline`, `CreateCommunity`,
  `JoinCommunity`, `LeaveCommunity`
- Chat: `GetChatRooms`, `GetChatRoomMessages`, `SendDirectMessage`,
  `SendGroupMessage`
- Discovery: `Search`, `SearchTypeahead`, `GetRecommendations`
- Media: `PrepareMediaUploading`, `GetMedia`, `GetStorageRateLimit`

## Sources used for schema validation

- The currently deployed `mixi.social` Next.js application shell
- Network behavior documented by the web client
- The MIT licensed `mixi2` npm package version 0.2.2, which includes a protobuf
  schema and unary client implementation

Because this interface is undocumented, every constant remains configurable
where practical, and raw response bytes are retained on typed responses.
