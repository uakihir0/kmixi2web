package work.socialhub.kmixi2web.api.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.PostMaskType
import work.socialhub.kmixi2web.entity.PostPublishingType
import kotlin.js.JsExport

@Serializable
@JsExport
class GetPostRequest(
    @ProtoNumber(1)
    var postId: String,
)

@Serializable
@JsExport
class GetPostsRequest(
    @ProtoNumber(1)
    var postIds: List<String> = emptyList(),
)

@Serializable
@JsExport
class GetRepliesRequest(
    @ProtoNumber(1)
    var postId: String,
    @ProtoNumber(2)
    var limit: Int? = null,
    @ProtoNumber(3)
    var cursor: String? = null,
)

@Serializable
@JsExport
class GetReplyAncestorsRequest(
    @ProtoNumber(1)
    var postId: String,
    @ProtoNumber(2)
    var limit: Int? = null,
)

@Serializable
@JsExport
class GetThreadPostsRequest(
    @ProtoNumber(1)
    var threadPostId: String,
    @ProtoNumber(2)
    var untilCursorId: String? = null,
    @ProtoNumber(3)
    var sinceCursorId: String? = null,
    @ProtoNumber(4)
    var limit: Int? = null,
    @ProtoNumber(5)
    var endCursorId: String? = null,
)

@Serializable
@JsExport
class CreatePostRequest(
    @ProtoNumber(1)
    var text: String,
    @ProtoNumber(2)
    var inReplyToPostId: String? = null,
    @ProtoNumber(3)
    var quotePostId: String? = null,
    @ProtoNumber(4)
    var mediaIds: List<String> = emptyList(),
    @ProtoNumber(5)
    var repostId: String? = null,
    @ProtoNumber(6)
    var isSensitive: Boolean = false,
    @ProtoNumber(7)
    var communityId: String? = null,
    @ProtoNumber(8)
    var attachedCommunityId: String? = null,
    @ProtoNumber(9)
    var decorations: List<Int> = emptyList(),
    @ProtoNumber(10)
    var maskType: PostMaskType? = null,
    @ProtoNumber(11)
    var maskCaption: String? = null,
    @ProtoNumber(12)
    var publishingType: PostPublishingType? = null,
)

@Serializable
@JsExport
class DeletePostRequest(
    @ProtoNumber(1)
    var postId: String,
)
