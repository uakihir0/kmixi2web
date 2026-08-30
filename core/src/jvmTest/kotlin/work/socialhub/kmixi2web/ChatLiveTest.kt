package work.socialhub.kmixi2web

import kotlinx.coroutines.runBlocking
import work.socialhub.kmixi2web.api.request.GetChatRoomMessagesRequest
import work.socialhub.kmixi2web.api.request.GetChatRoomRequest
import work.socialhub.kmixi2web.api.request.GetChatRoomsRequest
import work.socialhub.kmixi2web.api.request.GetUnreadChatRoomCountRequest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ChatLiveTest {
    @Test
    fun readChatRoomsAndMessages() = runBlocking {
        LiveTestSupport.requireMode("chat-read")
        val client = LiveTestSupport.client()

        val unread = client.chat().getUnreadChatRoomCount(GetUnreadChatRoomCountRequest())
        assertEquals(200, unread.status)
        assertTrue(unread.data.activeRoomCount >= 0)
        assertTrue(unread.data.requestedRoomCount >= 0)
        assertTrue(unread.data.mutedRoomCount >= 0)

        val rooms = client.chat().getChatRooms(GetChatRoomsRequest(limit = PAGE_SIZE))
        assertEquals(200, rooms.status)
        rooms.data.rooms.forEach { room ->
            assertTrue(room.roomId.isNotBlank())
            room.members.forEach { assertTrue(it.personaId.isNotBlank()) }
        }

        val first = rooms.data.rooms.firstOrNull()
        if (first == null) {
            println("The active persona has no chat room; nothing to read.")
            return@runBlocking
        }

        val room = client.chat().getChatRoom(GetChatRoomRequest(first.roomId))
        assertEquals(200, room.status)
        val detail = assertNotNull(room.data.room)
        assertEquals(first.roomId, detail.roomId)
        assertEquals(first.isGroup, detail.isGroup)

        val messages = client.chat().getChatRoomMessages(
            GetChatRoomMessagesRequest(
                roomId = first.roomId,
                limit = PAGE_SIZE,
            )
        )
        assertEquals(200, messages.status)
        messages.data.messages.forEach { message ->
            assertTrue(message.messageId.isNotBlank())
            assertEquals(first.roomId, message.roomId)
            assertTrue(message.personaId.isNotBlank())
            assertTrue(message.createdAt != null)
        }
        assertEquals(
            messages.data.messages.size,
            messages.data.messages.map { it.messageId }.distinct().size,
            "Chat history returned duplicate message IDs",
        )

        println(
            "Read ${rooms.data.rooms.size} room(s) and " +
                "${messages.data.messages.size} message(s) from ${first.roomId}; " +
                "unread active=${unread.data.activeRoomCount}."
        )
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
