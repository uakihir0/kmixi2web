package work.socialhub.kmixi2web.internal.api

import work.socialhub.kmixi2web.api.NotificationResource
import work.socialhub.kmixi2web.api.request.GetBadgeCountRequest
import work.socialhub.kmixi2web.api.request.GetNotificationsRequest
import work.socialhub.kmixi2web.api.request.MarkNotificationAsReadRequest
import work.socialhub.kmixi2web.api.request.MarkNotificationsAsReadBeforeTimeRequest
import work.socialhub.kmixi2web.api.response.GetBadgeCountResponse
import work.socialhub.kmixi2web.api.response.GetNotificationsResponse
import work.socialhub.kmixi2web.api.response.MarkNotificationAsReadResponse
import work.socialhub.kmixi2web.api.response.MarkNotificationsAsReadResponse
import work.socialhub.kmixi2web.entity.share.Response
import work.socialhub.kmixi2web.internal.MercuryClient
import work.socialhub.kmixi2web.util.toBlocking

internal class NotificationResourceImpl(
    private val client: MercuryClient,
) : NotificationResource {
    override suspend fun getNotifications(
        request: GetNotificationsRequest,
    ): Response<GetNotificationsResponse> {
        return client.call(
            "GetNotifications",
            request,
            GetNotificationsRequest.serializer(),
            GetNotificationsResponse.serializer(),
        )
    }

    override fun getNotificationsBlocking(
        request: GetNotificationsRequest,
    ) = toBlocking { getNotifications(request) }

    override suspend fun getBadgeCount(
        request: GetBadgeCountRequest,
    ): Response<GetBadgeCountResponse> {
        return client.call(
            "GetBadgeCount",
            request,
            GetBadgeCountRequest.serializer(),
            GetBadgeCountResponse.serializer(),
        )
    }

    override fun getBadgeCountBlocking(
        request: GetBadgeCountRequest,
    ) = toBlocking { getBadgeCount(request) }

    override suspend fun markNotificationAsRead(
        request: MarkNotificationAsReadRequest,
    ): Response<MarkNotificationAsReadResponse> {
        return client.call(
            "MarkNotificationAsRead",
            request,
            MarkNotificationAsReadRequest.serializer(),
            MarkNotificationAsReadResponse.serializer(),
        )
    }

    override fun markNotificationAsReadBlocking(
        request: MarkNotificationAsReadRequest,
    ) = toBlocking { markNotificationAsRead(request) }

    override suspend fun markNotificationsAsReadBeforeTime(
        request: MarkNotificationsAsReadBeforeTimeRequest,
    ): Response<MarkNotificationsAsReadResponse> {
        return client.call(
            "MarkNotificationsAsReadBeforeTime",
            request,
            MarkNotificationsAsReadBeforeTimeRequest.serializer(),
            MarkNotificationsAsReadResponse.serializer(),
        )
    }

    override fun markNotificationsAsReadBeforeTimeBlocking(
        request: MarkNotificationsAsReadBeforeTimeRequest,
    ) = toBlocking { markNotificationsAsReadBeforeTime(request) }
}
