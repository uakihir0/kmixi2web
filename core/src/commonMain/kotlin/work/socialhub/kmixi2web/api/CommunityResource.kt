package work.socialhub.kmixi2web.api

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
import kotlin.js.JsExport

@JsExport
interface CommunityResource {
    /**
     * Communities the persona belongs to. Defaults to the signed-in persona
     * when `personaId` is omitted.
     */
    suspend fun getParticipatingCommunities(
        request: GetParticipatingCommunitiesRequest,
    ): Response<GetParticipatingCommunitiesResponse>

    @JsExport.Ignore
    fun getParticipatingCommunitiesBlocking(
        request: GetParticipatingCommunitiesRequest,
    ): Response<GetParticipatingCommunitiesResponse>

    suspend fun getCommunity(
        request: GetCommunityRequest,
    ): Response<GetCommunityResponse>

    @JsExport.Ignore
    fun getCommunityBlocking(
        request: GetCommunityRequest,
    ): Response<GetCommunityResponse>

    suspend fun getCommunities(
        request: GetCommunitiesRequest,
    ): Response<GetCommunitiesResponse>

    @JsExport.Ignore
    fun getCommunitiesBlocking(
        request: GetCommunitiesRequest,
    ): Response<GetCommunitiesResponse>

    suspend fun getCommunityTimeline(
        request: GetCommunityTimelineRequest,
    ): Response<GetTimelineResponse>

    @JsExport.Ignore
    fun getCommunityTimelineBlocking(
        request: GetCommunityTimelineRequest,
    ): Response<GetTimelineResponse>

    suspend fun getParticipatingCommunityMembers(
        request: GetParticipatingCommunityMembersRequest,
    ): Response<GetParticipatingCommunityMembersResponse>

    @JsExport.Ignore
    fun getParticipatingCommunityMembersBlocking(
        request: GetParticipatingCommunityMembersRequest,
    ): Response<GetParticipatingCommunityMembersResponse>

    /**
     * Joins a public community. Communities whose access level is
     * `APPROVAL_REQUIRED` need [requestJoinCommunity] instead.
     */
    suspend fun joinCommunity(
        request: JoinCommunityRequest,
    ): Response<JoinCommunityResponse>

    @JsExport.Ignore
    fun joinCommunityBlocking(
        request: JoinCommunityRequest,
    ): Response<JoinCommunityResponse>

    suspend fun requestJoinCommunity(
        request: RequestJoinCommunityRequest,
    ): Response<RequestJoinCommunityResponse>

    @JsExport.Ignore
    fun requestJoinCommunityBlocking(
        request: RequestJoinCommunityRequest,
    ): Response<RequestJoinCommunityResponse>

    suspend fun leaveCommunity(
        request: LeaveCommunityRequest,
    ): Response<LeaveCommunityResponse>

    @JsExport.Ignore
    fun leaveCommunityBlocking(
        request: LeaveCommunityRequest,
    ): Response<LeaveCommunityResponse>
}
