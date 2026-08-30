package work.socialhub.kmixi2web.api.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.FeedSourceType
import work.socialhub.kmixi2web.entity.PostReactionType
import kotlin.js.JsExport

@Serializable
@JsExport
class GetSubscribingFeedsRequest(
    @ProtoNumber(1)
    var untilCursor: String? = null,
    @ProtoNumber(2)
    var limit: Int? = null,
    @ProtoNumber(3)
    var sinceCursor: String? = null,
    @ProtoNumber(4)
    var endCursor: String? = null,
    @ProtoNumber(5)
    var feedSourceType: FeedSourceType? = null,
)

@Serializable
@JsExport
class GetRecommendedTimelineRequest(
    @ProtoNumber(1)
    var untilCursorId: String? = null,
    @ProtoNumber(2)
    var sinceCursorId: String? = null,
    @ProtoNumber(3)
    var limit: Int? = null,
    @ProtoNumber(4)
    var endCursorId: String? = null,
)

@Serializable
@JsExport
class GetFollowingsTimelineRequest(
    @ProtoNumber(1)
    var untilCursorId: String? = null,
    @ProtoNumber(2)
    var sinceCursorId: String? = null,
    @ProtoNumber(3)
    var limit: Int? = null,
    @ProtoNumber(4)
    var endCursorId: String? = null,
)

@Serializable
@JsExport
class GetPersonalTimelineRequest(
    @ProtoNumber(1)
    var personaId: String,
    @ProtoNumber(2)
    var untilCursorId: String? = null,
    @ProtoNumber(3)
    var sinceCursorId: String? = null,
    @ProtoNumber(4)
    var limit: Int? = null,
    @ProtoNumber(5)
    var endCursorId: String? = null,
    @ProtoNumber(6)
    var mediaOnly: Boolean? = null,
)

@Serializable
@JsExport
class GetHashtagTimelineRequest(
    @ProtoNumber(1)
    var hashtag: String,
    @ProtoNumber(2)
    var mediaOnly: Boolean? = null,
    @ProtoNumber(3)
    var untilCursorId: String? = null,
    @ProtoNumber(4)
    var sinceCursorId: String? = null,
    @ProtoNumber(5)
    var limit: Int? = null,
    @ProtoNumber(6)
    var endCursorId: String? = null,
)

@Serializable
@JsExport
class GetReactionPostsRequest(
    @ProtoNumber(1)
    var reactionType: PostReactionType,
    @ProtoNumber(2)
    var limit: Int? = null,
    @ProtoNumber(3)
    var cursor: String? = null,
)
