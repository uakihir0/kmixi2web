package work.socialhub.kmixi2web.internal.api

import work.socialhub.kmixi2web.api.FollowResource
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
import work.socialhub.kmixi2web.internal.MercuryClient
import work.socialhub.kmixi2web.util.toBlocking

internal class FollowResourceImpl(
    private val client: MercuryClient,
) : FollowResource {
    override suspend fun getFollowings(
        request: GetFollowingsRequest,
    ): Response<GetFollowingsResponse> {
        return client.call(
            "GetFollowings",
            request,
            GetFollowingsRequest.serializer(),
            GetFollowingsResponse.serializer(),
        )
    }

    override fun getFollowingsBlocking(
        request: GetFollowingsRequest,
    ) = toBlocking { getFollowings(request) }

    override suspend fun getFollowers(
        request: GetFollowersRequest,
    ): Response<GetFollowersResponse> {
        return client.call(
            "GetFollowers",
            request,
            GetFollowersRequest.serializer(),
            GetFollowersResponse.serializer(),
        )
    }

    override fun getFollowersBlocking(
        request: GetFollowersRequest,
    ) = toBlocking { getFollowers(request) }

    override suspend fun createFollowing(
        request: CreateFollowingRequest,
    ): Response<CreateFollowingResponse> {
        return client.call(
            "CreateFollowing",
            request,
            CreateFollowingRequest.serializer(),
            CreateFollowingResponse.serializer(),
        )
    }

    override fun createFollowingBlocking(
        request: CreateFollowingRequest,
    ) = toBlocking { createFollowing(request) }

    override suspend fun deleteFollowing(
        request: DeleteFollowingRequest,
    ): Response<DeleteFollowingResponse> {
        return client.call(
            "DeleteFollowing",
            request,
            DeleteFollowingRequest.serializer(),
            DeleteFollowingResponse.serializer(),
        )
    }

    override fun deleteFollowingBlocking(
        request: DeleteFollowingRequest,
    ) = toBlocking { deleteFollowing(request) }

    override suspend fun sendFollowingRequest(
        request: SendFollowingRequestRequest,
    ): Response<SendFollowingRequestResponse> {
        return client.call(
            "SendFollowingRequest",
            request,
            SendFollowingRequestRequest.serializer(),
            SendFollowingRequestResponse.serializer(),
        )
    }

    override fun sendFollowingRequestBlocking(
        request: SendFollowingRequestRequest,
    ) = toBlocking { sendFollowingRequest(request) }

    override suspend fun cancelFollowingRequest(
        request: CancelFollowingRequestRequest,
    ): Response<CancelFollowingRequestResponse> {
        return client.call(
            "CancelFollowingRequest",
            request,
            CancelFollowingRequestRequest.serializer(),
            CancelFollowingRequestResponse.serializer(),
        )
    }

    override fun cancelFollowingRequestBlocking(
        request: CancelFollowingRequestRequest,
    ) = toBlocking { cancelFollowingRequest(request) }

    override suspend fun approveFollowingRequest(
        request: ApproveFollowingRequestRequest,
    ): Response<ApproveFollowingRequestResponse> {
        return client.call(
            "ApproveFollowingRequest",
            request,
            ApproveFollowingRequestRequest.serializer(),
            ApproveFollowingRequestResponse.serializer(),
        )
    }

    override fun approveFollowingRequestBlocking(
        request: ApproveFollowingRequestRequest,
    ) = toBlocking { approveFollowingRequest(request) }

    override suspend fun rejectFollowingRequest(
        request: RejectFollowingRequestRequest,
    ): Response<RejectFollowingRequestResponse> {
        return client.call(
            "RejectFollowingRequest",
            request,
            RejectFollowingRequestRequest.serializer(),
            RejectFollowingRequestResponse.serializer(),
        )
    }

    override fun rejectFollowingRequestBlocking(
        request: RejectFollowingRequestRequest,
    ) = toBlocking { rejectFollowingRequest(request) }

    override suspend fun getFollowingRequests(
        request: GetFollowingRequestsRequest,
    ): Response<GetFollowingRequestsResponse> {
        return client.call(
            "GetFollowingRequests",
            request,
            GetFollowingRequestsRequest.serializer(),
            GetFollowingRequestsResponse.serializer(),
        )
    }

    override fun getFollowingRequestsBlocking(
        request: GetFollowingRequestsRequest,
    ) = toBlocking { getFollowingRequests(request) }

    override suspend fun getPendingFollowingRequests(
        request: GetPendingFollowingRequestsRequest,
    ): Response<GetPendingFollowingRequestsResponse> {
        return client.call(
            "GetPendingFollowingRequests",
            request,
            GetPendingFollowingRequestsRequest.serializer(),
            GetPendingFollowingRequestsResponse.serializer(),
        )
    }

    override fun getPendingFollowingRequestsBlocking(
        request: GetPendingFollowingRequestsRequest,
    ) = toBlocking { getPendingFollowingRequests(request) }
}
