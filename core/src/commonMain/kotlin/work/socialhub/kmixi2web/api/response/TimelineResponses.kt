package work.socialhub.kmixi2web.api.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.Feed
import work.socialhub.kmixi2web.entity.Post
import kotlin.js.JsExport

@Serializable
@JsExport
class GetSubscribingFeedsResponse(
    @ProtoNumber(1)
    var feeds: List<Feed> = emptyList(),
    @ProtoNumber(2)
    var nextCursor: String? = null,
)

@Serializable
@JsExport
class GetTimelineResponse(
    @ProtoNumber(1)
    var posts: List<Post> = emptyList(),
)

@Serializable
@JsExport
class GetReactionPostsResponse(
    @ProtoNumber(1)
    var posts: List<Post> = emptyList(),
    @ProtoNumber(2)
    var nextCursor: String = "",
    @ProtoNumber(3)
    var hasNext: Boolean = false,
)
