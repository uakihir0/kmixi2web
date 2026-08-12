package work.socialhub.kmixi2web.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

@Serializable
@JsExport
class Feed(
    @ProtoNumber(1)
    var feedType: Int = 0,
    @ProtoNumber(2)
    var timeSeriesId: String = "",
    @ProtoNumber(3)
    var post: Post? = null,
    @ProtoNumber(4)
    var communityAggregationPost: CommunityAggregationPost? = null,
)
