package work.socialhub.kmixi2web.api

import work.socialhub.kmixi2web.api.request.ApproveFollowingRequestRequest
import work.socialhub.kmixi2web.api.request.CancelFollowingRequestRequest
import work.socialhub.kmixi2web.api.request.CreateFollowingRequest
import work.socialhub.kmixi2web.api.request.DeleteFollowingRequest
import work.socialhub.kmixi2web.api.request.GetFollowersRequest
import work.socialhub.kmixi2web.api.request.GetFollowingRequestsRequest
import work.socialhub.kmixi2web.api.request.GetFollowingsRequest
import work.socialhub.kmixi2web.api.request.GetPendingFollowingRequestsRequest
import work.socialhub.kmixi2web.api.request.RejectFollowingRequestRequest
import work.socialhub.kmixi2web.api.request.SendFollowingRequestRequest
import work.socialhub.kmixi2web.api.response.ApproveFollowingRequestResponse
import work.socialhub.kmixi2web.api.response.CancelFollowingRequestResponse
import work.socialhub.kmixi2web.api.response.CreateFollowingResponse
import work.socialhub.kmixi2web.api.response.DeleteFollowingResponse
import work.socialhub.kmixi2web.api.response.GetFollowersResponse
import work.socialhub.kmixi2web.api.response.GetFollowingRequestsResponse
import work.socialhub.kmixi2web.api.response.GetFollowingsResponse
import work.socialhub.kmixi2web.api.response.GetPendingFollowingRequestsResponse
import work.socialhub.kmixi2web.api.response.RejectFollowingRequestResponse
import work.socialhub.kmixi2web.api.response.SendFollowingRequestResponse
import work.socialhub.kmixi2web.entity.share.Response
import kotlin.js.JsExport

@JsExport
interface FollowResource {
    suspend fun getFollowings(
        request: GetFollowingsRequest,
    ): Response<GetFollowingsResponse>

    @JsExport.Ignore
    fun getFollowingsBlocking(
        request: GetFollowingsRequest,
    ): Response<GetFollowingsResponse>

    suspend fun getFollowers(
        request: GetFollowersRequest,
    ): Response<GetFollowersResponse>

    @JsExport.Ignore
    fun getFollowersBlocking(
        request: GetFollowersRequest,
    ): Response<GetFollowersResponse>

    suspend fun createFollowing(
        request: CreateFollowingRequest,
    ): Response<CreateFollowingResponse>

    @JsExport.Ignore
    fun createFollowingBlocking(
        request: CreateFollowingRequest,
    ): Response<CreateFollowingResponse>

    suspend fun deleteFollowing(
        request: DeleteFollowingRequest,
    ): Response<DeleteFollowingResponse>

    @JsExport.Ignore
    fun deleteFollowingBlocking(
        request: DeleteFollowingRequest,
    ): Response<DeleteFollowingResponse>

    suspend fun sendFollowingRequest(
        request: SendFollowingRequestRequest,
    ): Response<SendFollowingRequestResponse>

    @JsExport.Ignore
    fun sendFollowingRequestBlocking(
        request: SendFollowingRequestRequest,
    ): Response<SendFollowingRequestResponse>

    suspend fun cancelFollowingRequest(
        request: CancelFollowingRequestRequest,
    ): Response<CancelFollowingRequestResponse>

    @JsExport.Ignore
    fun cancelFollowingRequestBlocking(
        request: CancelFollowingRequestRequest,
    ): Response<CancelFollowingRequestResponse>

    suspend fun approveFollowingRequest(
        request: ApproveFollowingRequestRequest,
    ): Response<ApproveFollowingRequestResponse>

    @JsExport.Ignore
    fun approveFollowingRequestBlocking(
        request: ApproveFollowingRequestRequest,
    ): Response<ApproveFollowingRequestResponse>

    suspend fun rejectFollowingRequest(
        request: RejectFollowingRequestRequest,
    ): Response<RejectFollowingRequestResponse>

    @JsExport.Ignore
    fun rejectFollowingRequestBlocking(
        request: RejectFollowingRequestRequest,
    ): Response<RejectFollowingRequestResponse>

    suspend fun getFollowingRequests(
        request: GetFollowingRequestsRequest,
    ): Response<GetFollowingRequestsResponse>

    @JsExport.Ignore
    fun getFollowingRequestsBlocking(
        request: GetFollowingRequestsRequest,
    ): Response<GetFollowingRequestsResponse>

    suspend fun getPendingFollowingRequests(
        request: GetPendingFollowingRequestsRequest = GetPendingFollowingRequestsRequest(),
    ): Response<GetPendingFollowingRequestsResponse>

    @JsExport.Ignore
    fun getPendingFollowingRequestsBlocking(
        request: GetPendingFollowingRequestsRequest = GetPendingFollowingRequestsRequest(),
    ): Response<GetPendingFollowingRequestsResponse>
}
