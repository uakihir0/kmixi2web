package work.socialhub.kmixi2web.internal.api

import work.socialhub.kmixi2web.api.TimelineResource
import work.socialhub.kmixi2web.api.request.GetFollowingsTimelineRequest
import work.socialhub.kmixi2web.api.request.GetHashtagTimelineRequest
import work.socialhub.kmixi2web.api.request.GetPersonalTimelineRequest
import work.socialhub.kmixi2web.api.request.GetReactionPostsRequest
import work.socialhub.kmixi2web.api.request.GetRecommendedTimelineRequest
import work.socialhub.kmixi2web.api.request.GetSubscribingFeedsRequest
import work.socialhub.kmixi2web.api.response.GetReactionPostsResponse
import work.socialhub.kmixi2web.api.response.GetSubscribingFeedsResponse
import work.socialhub.kmixi2web.api.response.GetTimelineResponse
import work.socialhub.kmixi2web.entity.share.Response
import work.socialhub.kmixi2web.internal.MercuryClient
import work.socialhub.kmixi2web.util.toBlocking

internal class TimelineResourceImpl(
    private val client: MercuryClient,
) : TimelineResource {
    override suspend fun getSubscribingFeeds(
        request: GetSubscribingFeedsRequest,
    ): Response<GetSubscribingFeedsResponse> {
        return client.call(
            "GetSubscribingFeeds",
            request,
            GetSubscribingFeedsRequest.serializer(),
            GetSubscribingFeedsResponse.serializer(),
        )
    }

    override fun getSubscribingFeedsBlocking(
        request: GetSubscribingFeedsRequest,
    ) = toBlocking { getSubscribingFeeds(request) }

    override suspend fun getRecommendedTimeline(
        request: GetRecommendedTimelineRequest,
    ): Response<GetTimelineResponse> {
        return client.call(
            "GetRecommendedTimeline",
            request,
            GetRecommendedTimelineRequest.serializer(),
            GetTimelineResponse.serializer(),
        )
    }

    override fun getRecommendedTimelineBlocking(
        request: GetRecommendedTimelineRequest,
    ) = toBlocking { getRecommendedTimeline(request) }

    override suspend fun getFollowingsTimeline(
        request: GetFollowingsTimelineRequest,
    ): Response<GetTimelineResponse> {
        return client.call(
            "GetFollowingsTimeline",
            request,
            GetFollowingsTimelineRequest.serializer(),
            GetTimelineResponse.serializer(),
        )
    }

    override fun getFollowingsTimelineBlocking(
        request: GetFollowingsTimelineRequest,
    ) = toBlocking { getFollowingsTimeline(request) }

    override suspend fun getPersonalTimeline(
        request: GetPersonalTimelineRequest,
    ): Response<GetTimelineResponse> {
        return client.call(
            "GetPersonalTimeline",
            request,
            GetPersonalTimelineRequest.serializer(),
            GetTimelineResponse.serializer(),
        )
    }

    override fun getPersonalTimelineBlocking(
        request: GetPersonalTimelineRequest,
    ) = toBlocking { getPersonalTimeline(request) }

    override suspend fun getHashtagTimeline(
        request: GetHashtagTimelineRequest,
    ): Response<GetTimelineResponse> {
        return client.call(
            "GetHashtagTimeline",
            request,
            GetHashtagTimelineRequest.serializer(),
            GetTimelineResponse.serializer(),
        )
    }

    override fun getHashtagTimelineBlocking(
        request: GetHashtagTimelineRequest,
    ) = toBlocking { getHashtagTimeline(request) }

    override suspend fun getReactionPosts(
        request: GetReactionPostsRequest,
    ): Response<GetReactionPostsResponse> {
        return client.call(
            "GetReactionPosts",
            request,
            GetReactionPostsRequest.serializer(),
            GetReactionPostsResponse.serializer(),
        )
    }

    override fun getReactionPostsBlocking(
        request: GetReactionPostsRequest,
    ) = toBlocking { getReactionPosts(request) }
}
