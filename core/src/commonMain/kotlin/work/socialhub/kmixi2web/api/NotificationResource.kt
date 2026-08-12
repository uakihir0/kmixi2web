package work.socialhub.kmixi2web.api

import work.socialhub.kmixi2web.api.request.GetBadgeCountRequest
import work.socialhub.kmixi2web.api.request.GetNotificationsRequest
import work.socialhub.kmixi2web.api.request.MarkNotificationAsReadRequest
import work.socialhub.kmixi2web.api.request.MarkNotificationsAsReadBeforeTimeRequest
import work.socialhub.kmixi2web.api.response.GetBadgeCountResponse
import work.socialhub.kmixi2web.api.response.GetNotificationsResponse
import work.socialhub.kmixi2web.api.response.MarkNotificationAsReadResponse
import work.socialhub.kmixi2web.api.response.MarkNotificationsAsReadResponse
import work.socialhub.kmixi2web.entity.share.Response
import kotlin.js.JsExport

@JsExport
interface NotificationResource {
    suspend fun getNotifications(
        request: GetNotificationsRequest,
    ): Response<GetNotificationsResponse>

    @JsExport.Ignore
    fun getNotificationsBlocking(
        request: GetNotificationsRequest,
    ): Response<GetNotificationsResponse>

    suspend fun getBadgeCount(
        request: GetBadgeCountRequest = GetBadgeCountRequest(),
    ): Response<GetBadgeCountResponse>

    @JsExport.Ignore
    fun getBadgeCountBlocking(
        request: GetBadgeCountRequest = GetBadgeCountRequest(),
    ): Response<GetBadgeCountResponse>

    suspend fun markNotificationAsRead(
        request: MarkNotificationAsReadRequest,
    ): Response<MarkNotificationAsReadResponse>

    @JsExport.Ignore
    fun markNotificationAsReadBlocking(
        request: MarkNotificationAsReadRequest,
    ): Response<MarkNotificationAsReadResponse>

    suspend fun markNotificationsAsReadBeforeTime(
        request: MarkNotificationsAsReadBeforeTimeRequest,
    ): Response<MarkNotificationsAsReadResponse>

    @JsExport.Ignore
    fun markNotificationsAsReadBeforeTimeBlocking(
        request: MarkNotificationsAsReadBeforeTimeRequest,
    ): Response<MarkNotificationsAsReadResponse>
}
