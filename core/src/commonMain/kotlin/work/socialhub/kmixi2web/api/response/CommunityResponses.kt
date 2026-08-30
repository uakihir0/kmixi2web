package work.socialhub.kmixi2web.api.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.Community
import work.socialhub.kmixi2web.entity.CommunityMember
import kotlin.js.JsExport

@Serializable
@JsExport
class GetParticipatingCommunitiesResponse(
    @ProtoNumber(1)
    var communities: List<Community> = emptyList(),
    @ProtoNumber(2)
    var nextCursor: String? = null,
)

@Serializable
@JsExport
class GetCommunityResponse(
    @ProtoNumber(1)
    var community: Community? = null,
)

@Serializable
@JsExport
class GetCommunitiesResponse(
    @ProtoNumber(1)
    var communities: List<Community> = emptyList(),
)

@Serializable
@JsExport
class GetParticipatingCommunityMembersResponse(
    @ProtoNumber(1)
    var members: List<CommunityMember> = emptyList(),
    @ProtoNumber(2)
    var cursor: String? = null,
)

@Serializable
@JsExport
class JoinCommunityResponse(
    @ProtoNumber(1)
    var community: Community? = null,
)

@Serializable
@JsExport
class RequestJoinCommunityResponse(
    @ProtoNumber(1)
    var community: Community? = null,
)

@Serializable
@JsExport
class LeaveCommunityResponse(
    @ProtoNumber(1)
    var leftChildCommunityIds: List<String> = emptyList(),
)
