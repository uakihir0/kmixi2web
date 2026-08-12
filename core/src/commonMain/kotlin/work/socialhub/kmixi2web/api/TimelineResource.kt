package work.socialhub.kmixi2web.api

import work.socialhub.kmixi2web.api.request.GetFollowingsTimelineRequest
import work.socialhub.kmixi2web.api.request.GetHashtagTimelineRequest
import work.socialhub.kmixi2web.api.request.GetPersonalTimelineRequest
import work.socialhub.kmixi2web.api.request.GetRecommendedTimelineRequest
import work.socialhub.kmixi2web.api.request.GetSubscribingFeedsRequest
import work.socialhub.kmixi2web.api.response.GetSubscribingFeedsResponse
import work.socialhub.kmixi2web.api.response.GetTimelineResponse
import work.socialhub.kmixi2web.entity.share.Response
import kotlin.js.JsExport

@JsExport
interface TimelineResource {
    suspend fun getSubscribingFeeds(
        request: GetSubscribingFeedsRequest,
    ): Response<GetSubscribingFeedsResponse>

    @JsExport.Ignore
    fun getSubscribingFeedsBlocking(
        request: GetSubscribingFeedsRequest,
    ): Response<GetSubscribingFeedsResponse>

    suspend fun getRecommendedTimeline(
        request: GetRecommendedTimelineRequest,
    ): Response<GetTimelineResponse>

    @JsExport.Ignore
    fun getRecommendedTimelineBlocking(
        request: GetRecommendedTimelineRequest,
    ): Response<GetTimelineResponse>

    suspend fun getFollowingsTimeline(
        request: GetFollowingsTimelineRequest,
    ): Response<GetTimelineResponse>

    @JsExport.Ignore
    fun getFollowingsTimelineBlocking(
        request: GetFollowingsTimelineRequest,
    ): Response<GetTimelineResponse>

    suspend fun getPersonalTimeline(
        request: GetPersonalTimelineRequest,
    ): Response<GetTimelineResponse>

    @JsExport.Ignore
    fun getPersonalTimelineBlocking(
        request: GetPersonalTimelineRequest,
    ): Response<GetTimelineResponse>

    suspend fun getHashtagTimeline(
        request: GetHashtagTimelineRequest,
    ): Response<GetTimelineResponse>

    @JsExport.Ignore
    fun getHashtagTimelineBlocking(
        request: GetHashtagTimelineRequest,
    ): Response<GetTimelineResponse>
}
