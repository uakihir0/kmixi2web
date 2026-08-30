package work.socialhub.kmixi2web.api.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.CommunityType
import kotlin.js.JsExport

@Serializable
@JsExport
class GetParticipatingCommunitiesRequest(
    @ProtoNumber(1)
    var personaId: String? = null,
    @ProtoNumber(2)
    var type: CommunityType? = null,
    @ProtoNumber(3)
    var limit: Int? = null,
    @ProtoNumber(4)
    var cursor: String? = null,
    @ProtoNumber(5)
    var isAdminOnly: Boolean? = null,
    @ProtoNumber(6)
    var rejectArchived: Boolean? = null,
)

@Serializable
@JsExport
class GetCommunityRequest(
    @ProtoNumber(1)
    var communityId: String,
)

@Serializable
@JsExport
class GetCommunitiesRequest(
    @ProtoNumber(1)
    var communityIds: List<String> = emptyList(),
)

@Serializable
@JsExport
class GetCommunityTimelineRequest(
    @ProtoNumber(1)
    var communityId: String,
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
class GetParticipatingCommunityMembersRequest(
    @ProtoNumber(1)
    var communityId: String,
    @ProtoNumber(2)
    var limit: Int? = null,
    @ProtoNumber(3)
    var cursor: String? = null,
)

@Serializable
@JsExport
class JoinCommunityRequest(
    @ProtoNumber(1)
    var communityId: String,
    @ProtoNumber(2)
    var skipBlockingMemberCheck: Boolean = false,
)

@Serializable
@JsExport
class RequestJoinCommunityRequest(
    @ProtoNumber(1)
    var communityId: String,
    @ProtoNumber(2)
    var skipBlockingMemberCheck: Boolean = false,
)

@Serializable
@JsExport
class LeaveCommunityRequest(
    @ProtoNumber(1)
    var communityId: String,
)
