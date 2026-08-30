package work.socialhub.kmixi2web.api.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

@Serializable
@JsExport
class GetFollowingsRequest(
    @ProtoNumber(1)
    var cursorId: String? = null,
    @ProtoNumber(2)
    var limit: Int? = null,
    @ProtoNumber(3)
    var personaId: String? = null,
)

@Serializable
@JsExport
class GetFollowersRequest(
    @ProtoNumber(1)
    var cursorId: String? = null,
    @ProtoNumber(2)
    var limit: Int? = null,
    @ProtoNumber(3)
    var personaId: String? = null,
)

@Serializable
@JsExport
class CreateFollowingRequest(
    @ProtoNumber(1)
    var followingId: String,
)

@Serializable
@JsExport
class DeleteFollowingRequest(
    @ProtoNumber(1)
    var followingId: String,
)

@Serializable
@JsExport
class SendFollowingRequestRequest(
    @ProtoNumber(1)
    var personaId: String,
)

@Serializable
@JsExport
class CancelFollowingRequestRequest(
    @ProtoNumber(1)
    var personaId: String,
)

@Serializable
@JsExport
class ApproveFollowingRequestRequest(
    @ProtoNumber(1)
    var requestId: String,
)

@Serializable
@JsExport
class RejectFollowingRequestRequest(
    @ProtoNumber(1)
    var requestId: String,
)

@Serializable
@JsExport
class GetFollowingRequestsRequest(
    @ProtoNumber(1)
    var requestIds: List<String> = emptyList(),
)

@Serializable
@JsExport
class GetPendingFollowingRequestsRequest(
    @ProtoNumber(1)
    var cursor: String? = null,
)
