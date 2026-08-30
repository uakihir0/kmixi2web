package work.socialhub.kmixi2web

import work.socialhub.kmixi2web.api.request.GetChatRoomMessagesRequest
import work.socialhub.kmixi2web.api.request.GetChatRoomsRequest
import work.socialhub.kmixi2web.api.request.SendDirectMessageRequest
import work.socialhub.kmixi2web.api.request.SendGroupMessageRequest
import work.socialhub.kmixi2web.api.request.SendMessageToRoomRequest
import work.socialhub.kmixi2web.api.response.GetChatRoomMessagesResponse
import work.socialhub.kmixi2web.api.response.GetChatRoomsResponse
import work.socialhub.kmixi2web.api.response.GetUnreadChatRoomCountResponse
import work.socialhub.kmixi2web.entity.ChatRoomMessageType
import work.socialhub.kmixi2web.entity.ChatRoomStatus
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ChatWireTest {

    @Test
    fun chatRoomsRequestEncodesLimitAndCursor() {
        val encoded = wireProto.encodeToByteArray(
            GetChatRoomsRequest.serializer(),
            GetChatRoomsRequest(
                limit = 10,
                untilMessageId = "m",
            ),
        )

        assertContentEquals(
            bytes(
                0x08, 0x0A,
                0x12, 0x01, 0x6D,
            ),
            encoded,
        )
    }

    @Test
    fun chatRoomMessagesRequestEncodesRoomAndSinceCursor() {
        val encoded = wireProto.encodeToByteArray(
            GetChatRoomMessagesRequest.serializer(),
            GetChatRoomMessagesRequest(
                roomId = "r",
                limit = 30,
                sinceMessageId = "s",
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x72,
                0x10, 0x1E,
                0x22, 0x01, 0x73,
            ),
            encoded,
        )
    }

    @Test
    fun sendMessageToRoomRequestEncodesMediaIds() {
        val encoded = wireProto.encodeToByteArray(
            SendMessageToRoomRequest.serializer(),
            SendMessageToRoomRequest(
                roomId = "r",
                mediaIds = listOf("m"),
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x72,
                0x1A, 0x01, 0x6D,
            ),
            encoded,
        )
    }

    @Test
    fun sendDirectMessageRequestEncodesReceiverTextAndPost() {
        val encoded = wireProto.encodeToByteArray(
            SendDirectMessageRequest.serializer(),
            SendDirectMessageRequest(
                receiverId = "p",
                text = "hi",
                mediaIds = listOf("m1"),
                postId = "q",
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x70,
                0x12, 0x02, 0x68, 0x69,
                0x1A, 0x02, 0x6D, 0x31,
                0x22, 0x01, 0x71,
            ),
            encoded,
        )
    }

    @Test
    fun sendGroupMessageRequestRepeatsReceiverIds() {
        val encoded = wireProto.encodeToByteArray(
            SendGroupMessageRequest.serializer(),
            SendGroupMessageRequest(
                receiverIds = listOf("a", "b"),
                text = "x",
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x61,
                0x0A, 0x01, 0x62,
                0x12, 0x01, 0x78,
            ),
            encoded,
        )
    }

    @Test
    fun chatRoomsResponseDecodesRoomMembersAndStatus() {
        val decoded = wireProto.decodeFromByteArray(
            GetChatRoomsResponse.serializer(),
            bytes(
                0x0A, 0x14,
                0x0A, 0x01, 0x72,
                0x10, 0x01,
                0x1A, 0x01, 0x74,
                0x22, 0x06,
                0x0A, 0x01, 0x70,
                0x12, 0x01, 0x6D,
                0x38, 0x01,
                0x40, 0x01,
                0x10, 0x01,
            ),
        )

        val room = decoded.rooms.single()
        assertEquals("r", room.roomId)
        assertTrue(room.isGroup)
        assertEquals("t", room.title)
        assertEquals("p", room.members.single().personaId)
        assertEquals("m", room.members.single().readMessageId)
        assertEquals(ChatRoomStatus.ACCEPTED, room.status)
        assertTrue(room.isMute)
        assertTrue(decoded.hasNext)
    }

    @Test
    fun chatRoomMessagesResponseDecodesMessageTypeAndText() {
        val decoded = wireProto.decodeFromByteArray(
            GetChatRoomMessagesResponse.serializer(),
            bytes(
                0x0A, 0x0F,
                0x0A, 0x01, 0x72,
                0x12, 0x01, 0x6D,
                0x1A, 0x01, 0x70,
                0x20, 0x02,
                0x32, 0x02, 0x68, 0x69,
            ),
        )

        val message = decoded.messages.single()
        assertEquals("r", message.roomId)
        assertEquals("m", message.messageId)
        assertEquals("p", message.personaId)
        assertEquals(ChatRoomMessageType.SYSTEM_MESSAGE_JOIN, message.messageType)
        assertEquals("hi", message.text)
        assertFalse(decoded.hasNext)
    }

    @Test
    fun unreadChatRoomCountResponseDecodesEachBucket() {
        val decoded = wireProto.decodeFromByteArray(
            GetUnreadChatRoomCountResponse.serializer(),
            bytes(
                0x08, 0x02,
                0x10, 0x01,
                0x18, 0x03,
            ),
        )

        assertEquals(2L, decoded.activeRoomCount)
        assertEquals(1L, decoded.requestedRoomCount)
        assertEquals(3L, decoded.mutedRoomCount)
    }
}
