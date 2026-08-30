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
| `GetProfile` | persona ID | one profile |
| `GetProfileByName` | persona name | one profile |
| `UpdateProfile` | optional display name, profile text, status icon, status text, link | updated profile |

Timeline posts contain `persona_id`; clients can batch those IDs through
`GetPersonas`.

`Profile` wraps a `Persona` at field 1 and adds `following_count` (2),
`followed_count` (3), `text` (4), `profile_image_url` (5), `link` (6),
`persona_connectivity` (7), `is_muted` (8), `post_pins` (9), `is_blocking` (10),
`is_blocked` (11), `is_post_notification_target` (12), and `social_media` (13).
`UpdateProfileRequest` fields are all optional; unset fields are left unchanged.

### Session

| RPC | Request | Response |
| --- | --- | --- |
| `GetSession` | empty | managed personas, active persona ID, frozen flag |
| `SwitchPersona` | persona ID | empty |

`SessionResponse` carries `session_managed_personas` (1), `active_persona_id`
(2), and `is_account_frozen` (3). Each `SessionManagedPersona` holds a `Profile`
at field 1 and `is_shared_persona` at field 2. This is the only observed way to
resolve the authenticated persona; the `x-auth-key` header does not name it.

### Social graph

| RPC | Request | Response |
| --- | --- | --- |
| `GetFollowings` | cursor ID, limit, persona ID | followings and next cursor |
| `GetFollowers` | cursor ID, limit, persona ID | followers and next cursor |
| `CreateFollowing` | following ID | created following |
| `DeleteFollowing` | following ID | deleted following |
| `SendFollowingRequest` | persona ID | target persona |
| `CancelFollowingRequest` | persona ID | target persona |
| `ApproveFollowingRequest` | request ID | empty |
| `RejectFollowingRequest` | request ID | empty |
| `GetFollowingRequests` | repeated request IDs | repeated following requests |
| `GetPendingFollowingRequests` | optional cursor | following requests and next cursor |

`GetFollowingsRequest` and `GetFollowersRequest` share `cursor_id` (1), `limit`
(2), and `persona_id` (3); omitting `persona_id` reads the active persona.
Responses carry the list at field 1 and `cursor_id` at field 2. An empty
`cursor_id` marks the end of the list.

`Following` and `Follower` have the same layout: `persona_id` (1), `created_at`
(2), `persona` (3), and `persona_connectivity` (4). `PersonaConnectivity` is the
same message returned inside `Profile`, so a single list read already reports
mutual follow state.

`FollowingRequest` carries `request_id` (1), `sender_id` (2), `receiver_id` (3),
`created_at` (4), and `status` (5). Follow requests apply only to personas that
approve followers manually; `CreateFollowing` fails for them.

### Moderation

| RPC | Request | Response |
| --- | --- | --- |
| `MakePersonaBlock` | persona ID | updated profile |
| `MakePersonaUnblock` | persona ID | updated profile |
| `MakePersonaMute` | persona ID | updated persona |
| `MakePersonaUnmute` | persona ID | updated persona |
| `GetBlockPersonas` | empty | repeated persona IDs |
| `GetMutePersonas` | empty | repeated persona IDs |
| `ReportPost` | post ID, reason type, reason content, optional right infringement target | reported post |
| `ReportPersona` | persona ID, reason type, reason content, optional right infringement target | reported persona |

Block and mute requests carry `persona_id` at field 1. Block responses return a
`Profile`, so `is_muted` (8), `is_blocking` (10), and `is_blocked` (11) can be
read back from the mutation itself; mute responses return a bare `Persona`.

`GetBlockPersonas` and `GetMutePersonas` take an empty request and return only
persona IDs at field 1, so the IDs still need `GetPersonas` to be displayed.

Report requests use `post_id`/`persona_id` (1), `reason_type` (2),
`reason_content` (3), and `right_infringement_target` (4). Both enums are
contiguous: reason types run from unspecified `0` through spam `1` to right
infringement `7`, and the infringement target is unspecified `0`, self `1`, or
others `2`. Field 4 applies only to right-infringement reports and is omitted
otherwise.

### Search

| RPC | Request | Response |
| --- | --- | --- |
| `Search` | query, repeated search operations | repeated search results |
| `SearchTypeahead` | query | repeated typeahead items |

`SearchRequest` holds `query` (1) and `operations` (2). One call can mix
operations of different types; each `SearchOperation` carries `type` (1),
`operation_id` (2), `until_cursor` (3), `since_cursor` (4), `limit` (5),
`end_cursor` (6), `media_attached_only` (7), `start_time_after` (8),
`end_time_after` (9), `persona_option` (10), `post_option` (11), and
`event_option` (12).

`SearchType` starts at personas `0`, then posts `1`, communities `2`, topic `3`,
and event `4`. Because personas is the zero value it is omitted from the encoded
request, which the service reads as a persona search.

`SearchResult` repeats `operation_id` (1) so results can be matched back to the
requested operation, and carries `personas_result` (2), `posts_result` (3), and
`communities_result` (4). The library models the persona and post results; the
community result is left to `RawResource` until the `Community` message is
covered by wire tests. `PersonasResult` and `PostsResult` both use the list at
field 1 and `next_cursor` at field 2, and personas arrive as
`PersonaWithConnectivity` so follow state needs no extra call.

`SearchTypeaheadItem` carries `item_type` (1) and `persona` (2); persona is
currently the only item type.

### Post engagement

| RPC | Request | Response |
| --- | --- | --- |
| `CreateBookmark` | post ID | updated post |
| `DeleteBookmark` | post ID | updated post |
| `DeleteRepost` | reference post ID | deleted post ID and reference post |
| `GetQuotePosts` | post ID, limit, cursor | posts, next cursor, has-next flag |
| `GetReactionPosts` | reaction type, limit, cursor | posts, next cursor, has-next flag |
| `GetRepostingPersonas` | post ID, limit, cursor | personas, next cursor, has-next flag |

Bookmark requests use `post_id` at field 1 and return the updated `Post`.
`DeleteRepostRequest` instead takes `reference_post_id` — the reposted post, not
the repost itself — and the response returns `deleted_post_id` (1) with the
`reference_post` (2).

The three paged lookups share `post_id`/`reaction_type` (1), `limit` (2), and
`cursor` (3), and their responses share the list at field 1, `next_cursor` (2),
and `has_next` (3).

`GetReactionPosts` reads back the active persona's own reactions.
`PostReactionType` values are non-contiguous: unknown `0`, reply `100`, repost
`101`, quote `102`, like `200`, and bookmark `201`. The client uses the same
custom-serializer approach as notification activity types, so like encodes as
varint `0xC8 0x01` rather than a Kotlin enum ordinal.

## Other observed MercuryService groups

The service also exposes authentication, communities/events, chat, media upload
preparation, and remote-settings RPCs. They can be called with `RawResource`
immediately and should be promoted to typed resources only after their field
numbers are covered by offline wire tests.

Representative method names:

- Session: `Signin`, `SignOut`, `RefreshToken`
- Communities: `GetCommunity`, `GetCommunityTimeline`, `CreateCommunity`,
  `JoinCommunity`, `LeaveCommunity`
- Chat: `GetChatRooms`, `GetChatRoomMessages`, `SendDirectMessage`,
  `SendGroupMessage`
- Discovery: `GetRecommendations`
- Media: `PrepareMediaUploading`, `GetMedia`, `GetStorageRateLimit`

## Sources used for schema validation

- The currently deployed `mixi.social` Next.js application shell
- Network behavior documented by the web client
- The MIT licensed `mixi2` npm package version 0.2.2, which includes a protobuf
  schema and unary client implementation

Because this interface is undocumented, every constant remains configurable
where practical, and raw response bytes are retained on typed responses.
