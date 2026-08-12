package work.socialhub.kmixi2web

import kotlinx.serialization.protobuf.ProtoBuf
import work.socialhub.kmixi2web.api.request.AddStampToPostRequest
import work.socialhub.kmixi2web.api.request.CreatePostRequest
import work.socialhub.kmixi2web.api.request.GetLikingPersonasRequest
import work.socialhub.kmixi2web.api.request.GetNotificationsRequest
import work.socialhub.kmixi2web.api.request.GetPostRequest
import work.socialhub.kmixi2web.api.request.GetPostStampReactionsRequest
import work.socialhub.kmixi2web.api.request.GetStampsRequest
import work.socialhub.kmixi2web.api.request.GetSubscribingFeedsRequest
import work.socialhub.kmixi2web.api.request.MarkNotificationAsReadRequest
import work.socialhub.kmixi2web.api.request.MarkNotificationsAsReadBeforeTimeRequest
import work.socialhub.kmixi2web.api.response.GetBadgeCountResponse
import work.socialhub.kmixi2web.api.response.GetNotificationsResponse
import work.socialhub.kmixi2web.api.response.GetPostResponse
import work.socialhub.kmixi2web.api.response.GetPostStampReactionsResponse
import work.socialhub.kmixi2web.api.response.GetLikingPersonasResponse
import work.socialhub.kmixi2web.entity.FeedSourceType
import work.socialhub.kmixi2web.entity.LanguageCode
import work.socialhub.kmixi2web.entity.NotificationActivityType
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ProtoWireTest {
    private val proto = ProtoBuf {
        encodeDefaults = false
    }

    @Test
    fun getPostRequestMatchesMercuryWireFormat() {
        val encoded = proto.encodeToByteArray(
            GetPostRequest.serializer(),
            GetPostRequest("abc"),
        )

        assertContentEquals(
            bytes(0x0A, 0x03, 0x61, 0x62, 0x63),
            encoded,
        )
    }

    @Test
    fun subscribingFeedRequestUsesObservedFieldNumbers() {
        val encoded = proto.encodeToByteArray(
            GetSubscribingFeedsRequest.serializer(),
            GetSubscribingFeedsRequest(
                limit = 50,
                feedSourceType = FeedSourceType.FOLLOWING,
            ),
        )

        assertContentEquals(
            bytes(0x10, 0x32, 0x28, 0x01),
            encoded,
        )
    }

    @Test
    fun createPostRequestMatchesObservedFields() {
        val encoded = proto.encodeToByteArray(
            CreatePostRequest.serializer(),
            CreatePostRequest(
                text = "hi",
                inReplyToPostId = "p1",
                mediaIds = listOf("m1", "m2"),
                isSensitive = true,
                decorations = listOf(1, 2),
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x02, 0x68, 0x69,
                0x12, 0x02, 0x70, 0x31,
                0x22, 0x02, 0x6D, 0x31,
                0x22, 0x02, 0x6D, 0x32,
                0x30, 0x01,
                0x4A, 0x02, 0x01, 0x02,
            ),
            encoded,
        )
    }

    @Test
    fun postResponseIgnoresUnknownFields() {
        val decoded = proto.decodeFromByteArray(
            GetPostResponse.serializer(),
            bytes(
                0x0A, 0x09,
                0x0A, 0x01, 0x70,
                0x48, 0x03,
                0x62, 0x02, 0x68, 0x69,
                0xA0, 0x06, 0x07,
            ),
        )

        assertEquals("p", decoded.post?.postId)
        assertEquals(3, decoded.post?.likesCount)
        assertEquals("hi", decoded.post?.text)
    }

    @Test
    fun stampReactionRequestMatchesObservedFields() {
        val encoded = proto.encodeToByteArray(
            GetPostStampReactionsRequest.serializer(),
            GetPostStampReactionsRequest(
                postId = "p",
                cursor = "c",
                limit = 50,
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x70,
                0x12, 0x01, 0x63,
                0x18, 0x32,
            ),
            encoded,
        )
    }

    @Test
    fun stampCatalogRequestMatchesObservedFields() {
        val encoded = proto.encodeToByteArray(
            GetStampsRequest.serializer(),
            GetStampsRequest(
                officialStampLanguage = LanguageCode.JP,
                communityIds = listOf("c1", "c2"),
            ),
        )

        assertContentEquals(
            bytes(
                0x08, 0x01,
                0x12, 0x02, 0x63, 0x31,
                0x12, 0x02, 0x63, 0x32,
            ),
            encoded,
        )
    }

    @Test
    fun reactionMutationRequestMatchesObservedFields() {
        val encoded = proto.encodeToByteArray(
            AddStampToPostRequest.serializer(),
            AddStampToPostRequest(
                postId = "p",
                stampId = "s",
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x70,
                0x12, 0x01, 0x73,
            ),
            encoded,
        )
    }

    @Test
    fun likingPersonasRequestMatchesObservedFields() {
        val encoded = proto.encodeToByteArray(
            GetLikingPersonasRequest.serializer(),
            GetLikingPersonasRequest(
                postId = "p",
                limit = 50,
                cursor = "c",
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x70,
                0x10, 0x32,
                0x1A, 0x01, 0x63,
            ),
            encoded,
        )
    }

    @Test
    fun notificationRequestPreservesNonSequentialActivityValues() {
        val encoded = proto.encodeToByteArray(
            GetNotificationsRequest.serializer(),
            GetNotificationsRequest(
                activityType = NotificationActivityType.REPLY,
                limit = 50,
                untilTimeSeriesId = "u",
                endTimeSeriesId = "e",
                activityTypes = listOf(
                    NotificationActivityType.LIKE,
                    NotificationActivityType.REACTION,
                ),
            ),
        )

        assertContentEquals(
            bytes(
                0x08, 0xCC, 0x01,
                0x10, 0x32,
                0x1A, 0x01, 0x75,
                0x22, 0x01, 0x65,
                0x2A, 0x04, 0xC8, 0x01, 0xCE, 0x01,
            ),
            encoded,
        )
    }

    @Test
    fun markNotificationRequestsUseTimeSeriesIdField() {
        val single = proto.encodeToByteArray(
            MarkNotificationAsReadRequest.serializer(),
            MarkNotificationAsReadRequest(timeSeriesId = "t"),
        )
        val beforeTime = proto.encodeToByteArray(
            MarkNotificationsAsReadBeforeTimeRequest.serializer(),
            MarkNotificationsAsReadBeforeTimeRequest(
                latestTimeSeriesId = "t",
            ),
        )

        val expected = bytes(0x0A, 0x01, 0x74)
        assertContentEquals(expected, single)
        assertContentEquals(expected, beforeTime)
    }

    @Test
    fun postResponseDecodesStampCountAndImageUrl() {
        val decoded = proto.decodeFromByteArray(
            GetPostResponse.serializer(),
            bytes(
                0x0A, 0x10,
                0x0A, 0x01, 0x70,
                0xEA, 0x01, 0x0A,
                0x0A, 0x06,
                0x0A, 0x01, 0x73,
                0x12, 0x01, 0x75,
                0x10, 0x03,
            ),
        )

        val stamp = decoded.post?.stamps?.single()
        assertEquals("s", stamp?.stamp?.stampId)
        assertEquals("u", stamp?.stamp?.url)
        assertEquals(3, stamp?.count)
    }

    @Test
    fun stampReactionResponseDecodesSummaryPersonaAndCursor() {
        val decoded = proto.decodeFromByteArray(
            GetPostStampReactionsResponse.serializer(),
            bytes(
                0x0A, 0x0A,
                0x0A, 0x06,
                0x0A, 0x01, 0x73,
                0x12, 0x01, 0x75,
                0x10, 0x03,
                0x12, 0x08,
                0x0A, 0x01, 0x73,
                0x12, 0x03,
                0x0A, 0x01, 0x70,
                0x1A, 0x01, 0x63,
            ),
        )

        assertEquals("s", decoded.stamps.single().stamp?.stampId)
        assertEquals("u", decoded.stamps.single().stamp?.url)
        assertEquals(3, decoded.stamps.single().count)
        assertEquals("s", decoded.stampReactions.single().stampId)
        assertEquals("p", decoded.stampReactions.single().persona?.personaId)
        assertEquals("c", decoded.nextCursor)
    }

    @Test
    fun likingPersonasResponseDecodesPagination() {
        val decoded = proto.decodeFromByteArray(
            GetLikingPersonasResponse.serializer(),
            bytes(
                0x0A, 0x03,
                0x0A, 0x01, 0x70,
                0x12, 0x01, 0x63,
                0x18, 0x01,
            ),
        )

        assertEquals("p", decoded.personas.single().personaId)
        assertEquals("c", decoded.nextCursor)
        assertEquals(true, decoded.hasNext)
    }

    @Test
    fun notificationResponseDecodesPostAndReactionImage() {
        val decoded = proto.decodeFromByteArray(
            GetNotificationsResponse.serializer(),
            bytes(
                0x0A, 0x18,
                0x08, 0xC8, 0x01,
                0x12, 0x02, 0x08, 0x01,
                0x1A, 0x01, 0x74,
                0x22, 0x01, 0x69,
                0x2A, 0x01, 0x70,
                0x42, 0x06,
                0x0A, 0x01, 0x73,
                0x12, 0x01, 0x75,
                0x10, 0x01,
            ),
        )

        val notification = decoded.notifications.single()
        assertEquals(NotificationActivityType.LIKE, notification.activityType)
        assertEquals(1, notification.createdAt?.seconds)
        assertEquals("t", notification.timeSeriesId)
        assertEquals("i", notification.issuerId)
        assertEquals("p", notification.postId)
        assertEquals("s", notification.reaction?.stampId)
        assertEquals("u", notification.reaction?.imageUrl)
        assertEquals(true, decoded.hasNext)
    }

    @Test
    fun badgeCountResponseDecodesPackedUnreadList() {
        val decoded = proto.decodeFromByteArray(
            GetBadgeCountResponse.serializer(),
            bytes(
                0x08, 0x01,
                0x10, 0x02,
                0x18, 0x03,
                0x20, 0x04,
                0x28, 0x05,
                0x32, 0x03, 0x01, 0x00, 0x01,
            ),
        )

        assertEquals(1, decoded.accountUnreadCount)
        assertEquals(2, decoded.personaUnreadNotificationCount)
        assertEquals(3, decoded.personaUnreadActiveRoomCount)
        assertEquals(4, decoded.personaUnreadRequestedRoomCount)
        assertEquals(5, decoded.personaUnreadMutedRoomCount)
        assertEquals(listOf(true, false, true), decoded.unreadList)
    }

    private fun bytes(vararg values: Int): ByteArray {
        return ByteArray(values.size) { index -> values[index].toByte() }
    }
}
