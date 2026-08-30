package work.socialhub.kmixi2web.internal.api

import work.socialhub.kmixi2web.api.CommunityResource
import work.socialhub.kmixi2web.api.request.GetCommunitiesRequest
import work.socialhub.kmixi2web.api.request.GetCommunityRequest
import work.socialhub.kmixi2web.api.request.GetCommunityTimelineRequest
import work.socialhub.kmixi2web.api.request.GetParticipatingCommunitiesRequest
import work.socialhub.kmixi2web.api.request.GetParticipatingCommunityMembersRequest
import work.socialhub.kmixi2web.api.request.JoinCommunityRequest
import work.socialhub.kmixi2web.api.request.LeaveCommunityRequest
import work.socialhub.kmixi2web.api.request.RequestJoinCommunityRequest
import work.socialhub.kmixi2web.api.response.GetCommunitiesResponse
import work.socialhub.kmixi2web.api.response.GetCommunityResponse
import work.socialhub.kmixi2web.api.response.GetParticipatingCommunitiesResponse
import work.socialhub.kmixi2web.api.response.GetParticipatingCommunityMembersResponse
import work.socialhub.kmixi2web.api.response.GetTimelineResponse
import work.socialhub.kmixi2web.api.response.JoinCommunityResponse
import work.socialhub.kmixi2web.api.response.LeaveCommunityResponse
import work.socialhub.kmixi2web.api.response.RequestJoinCommunityResponse
import work.socialhub.kmixi2web.entity.share.Response
import work.socialhub.kmixi2web.internal.MercuryClient
import work.socialhub.kmixi2web.util.toBlocking

internal class CommunityResourceImpl(
    private val client: MercuryClient,
) : CommunityResource {
    override suspend fun getParticipatingCommunities(
        request: GetParticipatingCommunitiesRequest,
    ): Response<GetParticipatingCommunitiesResponse> {
        return client.call(
            "GetParticipatingCommunities",
            request,
            GetParticipatingCommunitiesRequest.serializer(),
            GetParticipatingCommunitiesResponse.serializer(),
        )
    }

    override fun getParticipatingCommunitiesBlocking(
        request: GetParticipatingCommunitiesRequest,
    ) = toBlocking { getParticipatingCommunities(request) }

    override suspend fun getCommunity(
        request: GetCommunityRequest,
    ): Response<GetCommunityResponse> {
        return client.call(
            "GetCommunity",
            request,
            GetCommunityRequest.serializer(),
            GetCommunityResponse.serializer(),
        )
    }

    override fun getCommunityBlocking(
        request: GetCommunityRequest,
    ) = toBlocking { getCommunity(request) }

    override suspend fun getCommunities(
        request: GetCommunitiesRequest,
    ): Response<GetCommunitiesResponse> {
        return client.call(
            "GetCommunities",
            request,
            GetCommunitiesRequest.serializer(),
            GetCommunitiesResponse.serializer(),
        )
    }

    override fun getCommunitiesBlocking(
        request: GetCommunitiesRequest,
    ) = toBlocking { getCommunities(request) }

    override suspend fun getCommunityTimeline(
        request: GetCommunityTimelineRequest,
    ): Response<GetTimelineResponse> {
        return client.call(
            "GetCommunityTimeline",
            request,
            GetCommunityTimelineRequest.serializer(),
            GetTimelineResponse.serializer(),
        )
    }

    override fun getCommunityTimelineBlocking(
        request: GetCommunityTimelineRequest,
    ) = toBlocking { getCommunityTimeline(request) }

    override suspend fun getParticipatingCommunityMembers(
        request: GetParticipatingCommunityMembersRequest,
    ): Response<GetParticipatingCommunityMembersResponse> {
        return client.call(
            "GetParticipatingCommunityMembers",
            request,
            GetParticipatingCommunityMembersRequest.serializer(),
            GetParticipatingCommunityMembersResponse.serializer(),
        )
    }

    override fun getParticipatingCommunityMembersBlocking(
        request: GetParticipatingCommunityMembersRequest,
    ) = toBlocking { getParticipatingCommunityMembers(request) }

    override suspend fun joinCommunity(
        request: JoinCommunityRequest,
    ): Response<JoinCommunityResponse> {
        return client.call(
            "JoinCommunity",
            request,
            JoinCommunityRequest.serializer(),
            JoinCommunityResponse.serializer(),
        )
    }

    override fun joinCommunityBlocking(
        request: JoinCommunityRequest,
    ) = toBlocking { joinCommunity(request) }

    override suspend fun requestJoinCommunity(
        request: RequestJoinCommunityRequest,
    ): Response<RequestJoinCommunityResponse> {
        return client.call(
            "RequestJoinCommunity",
            request,
            RequestJoinCommunityRequest.serializer(),
            RequestJoinCommunityResponse.serializer(),
        )
    }

    override fun requestJoinCommunityBlocking(
        request: RequestJoinCommunityRequest,
    ) = toBlocking { requestJoinCommunity(request) }

    override suspend fun leaveCommunity(
        request: LeaveCommunityRequest,
    ): Response<LeaveCommunityResponse> {
        return client.call(
            "LeaveCommunity",
            request,
            LeaveCommunityRequest.serializer(),
            LeaveCommunityResponse.serializer(),
        )
    }

    override fun leaveCommunityBlocking(
        request: LeaveCommunityRequest,
    ) = toBlocking { leaveCommunity(request) }
}
