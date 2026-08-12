package work.socialhub.kmixi2web

import kotlinx.coroutines.runBlocking
import work.socialhub.kmixi2web.api.request.GetBadgeCountRequest
import work.socialhub.kmixi2web.api.request.GetNotificationsRequest
import work.socialhub.kmixi2web.entity.Notification
import work.socialhub.kmixi2web.entity.NotificationActivityType
import java.net.HttpURLConnection
import java.net.URI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class NotificationLiveTest {
    @Test
    fun readNotificationsAndBadgeCounts() = runBlocking {
        LiveTestSupport.requireMode("notification-read")
        val client = LiveTestSupport.client()

        val badge = client.notification().getBadgeCount(GetBadgeCountRequest())
        assertEquals(200, badge.status)
        assertTrue(badge.data.accountUnreadCount >= 0)
        assertTrue(badge.data.personaUnreadNotificationCount >= 0)
        assertTrue(badge.data.personaUnreadActiveRoomCount >= 0)
        assertTrue(badge.data.personaUnreadRequestedRoomCount >= 0)
        assertTrue(badge.data.personaUnreadMutedRoomCount >= 0)

        val notifications = mutableListOf<Notification>()
        var cursor: String? = null
        var hasNext = false
        var pages = 0
        do {
            val response = client.notification().getNotifications(
                GetNotificationsRequest(
                    limit = PAGE_SIZE,
                    untilTimeSeriesId = cursor,
                )
            )
            assertEquals(200, response.status)
            response.data.notifications.verify()
            notifications += response.data.notifications
            hasNext = response.data.hasNext
            pages += 1

            val nextCursor = response.data.notifications.lastOrNull()?.timeSeriesId
            if (hasNext) {
                assertTrue(!nextCursor.isNullOrBlank())
                assertNotEquals(cursor, nextCursor)
            }
            cursor = nextCursor
        } while (hasNext && pages < MAX_PAGES)

        assertTrue(notifications.isNotEmpty(), "Expected at least one notification")
        assertEquals(
            notifications.size,
            notifications.map { it.timeSeriesId }.distinct().size,
            "Notification pagination returned duplicate time-series IDs",
        )

        val filterType = notifications
            .map { it.activityType }
            .firstOrNull { it != NotificationActivityType.UNKNOWN }
        if (filterType != null) {
            val filtered = client.notification().getNotifications(
                GetNotificationsRequest(
                    limit = PAGE_SIZE,
                    activityTypes = listOf(filterType),
                )
            )
            assertEquals(200, filtered.status)
            assertTrue(
                filtered.data.notifications.all {
                    it.activityType == filterType
                }
            )
        }

        notifications
            .mapNotNull { it.reaction?.imageUrl }
            .firstOrNull { it.isWebUrl() }
            ?.let(::assertImageUrlReachable)

        println(
            "Read ${notifications.size} notifications across $pages page(s); " +
                "hasNext=$hasNext, unread=${badge.data.personaUnreadNotificationCount}, " +
                "types=${notifications.map { it.activityType }.distinct().size}."
        )
    }

    private fun List<Notification>.verify() {
        forEach { notification ->
            assertTrue(notification.timeSeriesId.isNotBlank())
            assertTrue(notification.issuerId.isNotBlank())
            assertTrue(notification.createdAt != null)
            notification.reaction?.imageUrl
                ?.takeIf { it.isNotBlank() }
                ?.let { assertTrue(it.isWebUrl()) }
        }
    }

    private fun String.isWebUrl(): Boolean {
        return startsWith("https://") || startsWith("http://")
    }

    private fun assertImageUrlReachable(url: String) {
        val connection = URI(url).toURL().openConnection() as HttpURLConnection
        try {
            connection.requestMethod = "HEAD"
            connection.instanceFollowRedirects = true
            connection.connectTimeout = 10_000
            connection.readTimeout = 10_000
            connection.setRequestProperty("User-Agent", "kmixi2web-live-test")
            assertTrue(
                connection.responseCode in 200..399,
                "Notification image returned HTTP ${connection.responseCode}: $url",
            )
        } finally {
            connection.disconnect()
        }
    }

    companion object {
        private const val PAGE_SIZE = 50
        private const val MAX_PAGES = 10
    }
}
