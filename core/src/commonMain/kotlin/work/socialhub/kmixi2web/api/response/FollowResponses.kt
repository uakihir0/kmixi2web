package work.socialhub.kmixi2web.api.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.Follower
import work.socialhub.kmixi2web.entity.Following
import work.socialhub.kmixi2web.entity.FollowingRequest
import work.socialhub.kmixi2web.entity.Persona
import kotlin.js.JsExport

@Serializable
@JsExport
class GetFollowingsResponse(
    @ProtoNumber(1)
    var followings: List<Following> = emptyList(),
    @ProtoNumber(2)
    var cursorId: String = "",
)

@Serializable
@JsExport
class GetFollowersResponse(
    @ProtoNumber(1)
    var followers: List<Follower> = emptyList(),
    @ProtoNumber(2)
    var cursorId: String = "",
)

@Serializable
@JsExport
class CreateFollowingResponse(
    @ProtoNumber(1)
    var following: Following? = null,
)

@Serializable
@JsExport
class DeleteFollowingResponse(
    @ProtoNumber(1)
    var following: Following? = null,
)

@Serializable
@JsExport
class SendFollowingRequestResponse(
    @ProtoNumber(1)
    var persona: Persona? = null,
)

@Serializable
@JsExport
class CancelFollowingRequestResponse(
    @ProtoNumber(1)
    var persona: Persona? = null,
)

@Serializable
@JsExport
class ApproveFollowingRequestResponse

@Serializable
@JsExport
class RejectFollowingRequestResponse

@Serializable
@JsExport
class GetFollowingRequestsResponse(
    @ProtoNumber(1)
    var followingRequests: List<FollowingRequest> = emptyList(),
)

@Serializable
@JsExport
class GetPendingFollowingRequestsResponse(
    @ProtoNumber(1)
    var followingRequests: List<FollowingRequest> = emptyList(),
    @ProtoNumber(2)
    var nextCursor: String? = null,
)
