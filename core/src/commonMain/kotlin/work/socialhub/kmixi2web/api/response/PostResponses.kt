package work.socialhub.kmixi2web.api.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.Post
import kotlin.js.JsExport

@Serializable
@JsExport
class GetPostResponse(
    @ProtoNumber(1)
    var post: Post? = null,
)

@Serializable
@JsExport
class GetPostsResponse(
    @ProtoNumber(1)
    var posts: List<Post> = emptyList(),
)

@Serializable
@JsExport
class GetRepliesResponse(
    @ProtoNumber(1)
    var posts: List<Post> = emptyList(),
    @ProtoNumber(2)
    var nextCursor: String = "",
    @ProtoNumber(3)
    var hasNext: Boolean = false,
    @ProtoNumber(4)
    var repliesFromOriginalSender: List<Post> = emptyList(),
)

@Serializable
@JsExport
class GetReplyAncestorsResponse(
    @ProtoNumber(1)
    var posts: List<Post> = emptyList(),
    @ProtoNumber(2)
    var basePost: Post? = null,
)

@Serializable
@JsExport
class CreatePostResponse(
    @ProtoNumber(1)
    var post: Post? = null,
    @ProtoNumber(2)
    var isPending: Boolean = false,
)

@Serializable
@JsExport
class DeletePostResponse(
    @ProtoNumber(1)
    var deleted: Boolean = false,
)
