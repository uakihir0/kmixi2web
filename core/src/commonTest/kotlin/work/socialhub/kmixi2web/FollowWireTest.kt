package work.socialhub.kmixi2web

import work.socialhub.kmixi2web.api.request.ApproveFollowingRequestRequest
import work.socialhub.kmixi2web.api.request.CreateFollowingRequest
import work.socialhub.kmixi2web.api.request.GetFollowingRequestsRequest
import work.socialhub.kmixi2web.api.request.GetFollowingsRequest
import work.socialhub.kmixi2web.api.request.SendFollowingRequestRequest
import work.socialhub.kmixi2web.api.response.GetFollowersResponse
import work.socialhub.kmixi2web.api.response.GetFollowingsResponse
import work.socialhub.kmixi2web.api.response.GetPendingFollowingRequestsResponse
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class FollowWireTest {

    @Test
    fun getFollowingsRequestUsesCursorLimitPersonaFields() {
        val encoded = wireProto.encodeToByteArray(
            GetFollowingsRequest.serializer(),
            GetFollowingsRequest(
                cursorId = "c",
                limit = 20,
                personaId = "p",
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x63,
                0x10, 0x14,
                0x1A, 0x01, 0x70,
            ),
            encoded,
        )
    }

    @Test
    fun getFollowingsRequestOmitsUnsetFields() {
        val encoded = wireProto.encodeToByteArray(
            GetFollowingsRequest.serializer(),
            GetFollowingsRequest(personaId = "p"),
        )

        assertContentEquals(
            bytes(0x1A, 0x01, 0x70),
            encoded,
        )
    }

    @Test
    fun followingMutationRequestsUseSingleTargetField() {
        val create = wireProto.encodeToByteArray(
            CreateFollowingRequest.serializer(),
            CreateFollowingRequest(followingId = "p"),
        )
        val send = wireProto.encodeToByteArray(
            SendFollowingRequestRequest.serializer(),
            SendFollowingRequestRequest(personaId = "p"),
        )
        val approve = wireProto.encodeToByteArray(
            ApproveFollowingRequestRequest.serializer(),
            ApproveFollowingRequestRequest(requestId = "p"),
        )

        val expected = bytes(0x0A, 0x01, 0x70)
        assertContentEquals(expected, create)
        assertContentEquals(expected, send)
        assertContentEquals(expected, approve)
    }

    @Test
    fun getFollowingRequestsRequestRepeatsRequestIds() {
        val encoded = wireProto.encodeToByteArray(
            GetFollowingRequestsRequest.serializer(),
            GetFollowingRequestsRequest(requestIds = listOf("a", "b")),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x61,
                0x0A, 0x01, 0x62,
            ),
            encoded,
        )
    }

    @Test
    fun getFollowingsResponseDecodesPersonaAndCursor() {
        val decoded = wireProto.decodeFromByteArray(
            GetFollowingsResponse.serializer(),
            bytes(
                0x0A, 0x12,
                0x0A, 0x01, 0x70,
                0x12, 0x02,
                0x08, 0x64,
                0x1A, 0x03,
                0x0A, 0x01, 0x70,
                0x22, 0x04,
                0x08, 0x01,
                0x20, 0x01,
                0x12, 0x01, 0x63,
            ),
        )

        val following = decoded.followings.single()
        assertEquals("p", following.personaId)
        assertEquals(100, following.createdAt?.seconds)
        assertEquals("p", following.persona?.personaId)
        assertEquals(true, following.connectivity?.following)
        assertEquals(true, following.connectivity?.followed)
        assertEquals("c", decoded.cursorId)
    }

    @Test
    fun getFollowersResponseDecodesFollowersList() {
        val decoded = wireProto.decodeFromByteArray(
            GetFollowersResponse.serializer(),
            bytes(
                0x0A, 0x03,
                0x0A, 0x01, 0x70,
                0x12, 0x01, 0x63,
            ),
        )

        assertEquals("p", decoded.followers.single().personaId)
        assertEquals("c", decoded.cursorId)
    }

    @Test
    fun pendingFollowingRequestsResponseDecodesRequestsAndCursor() {
        val decoded = wireProto.decodeFromByteArray(
            GetPendingFollowingRequestsResponse.serializer(),
            bytes(
                0x0A, 0x0F,
                0x0A, 0x01, 0x72,
                0x12, 0x01, 0x73,
                0x1A, 0x01, 0x74,
                0x22, 0x02,
                0x08, 0x64,
                0x28, 0x01,
                0x12, 0x01, 0x63,
            ),
        )

        val request = decoded.followingRequests.single()
        assertEquals("r", request.requestId)
        assertEquals("s", request.senderId)
        assertEquals("t", request.receiverId)
        assertEquals(100, request.createdAt?.seconds)
        assertEquals(1, request.status)
        assertEquals("c", decoded.nextCursor)
    }
}
