package work.socialhub.kmixi2web.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

@Serializable
@JsExport
class CommunitySummary(
    @ProtoNumber(1)
    var communityId: String = "",
    @ProtoNumber(2)
    var name: String = "",
    @ProtoNumber(3)
    var accessLevel: Int = 0,
    @ProtoNumber(4)
    var isArchived: Boolean = false,
    @ProtoNumber(6)
    var coverImage: Media? = null,
    @ProtoNumber(7)
    var type: Int = 0,
    @ProtoNumber(9)
    var parent: CommunitySummary? = null,
    @ProtoNumber(10)
    var postConstraint: Int = 0,
    @ProtoNumber(11)
    var creatorId: String? = null,
)

@Serializable
@JsExport
class CommunityAggregationPost(
    @ProtoNumber(1)
    var communityId: String = "",
    @ProtoNumber(2)
    var post: Post? = null,
    @ProtoNumber(3)
    var aggregationCount: Long = 0,
    @ProtoNumber(4)
    var hasMore: Boolean = false,
    @ProtoNumber(5)
    var community: CommunitySummary? = null,
    @ProtoNumber(6)
    var untilCursor: String = "",
    @ProtoNumber(7)
    var endCursor: String = "",
)
