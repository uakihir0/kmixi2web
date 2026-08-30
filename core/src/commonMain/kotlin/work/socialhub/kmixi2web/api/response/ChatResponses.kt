package work.socialhub.kmixi2web.api.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.ChatRoom
import work.socialhub.kmixi2web.entity.ChatRoomMessage
import kotlin.js.JsExport

@Serializable
@JsExport
class GetChatRoomsResponse(
    @ProtoNumber(1)
    var rooms: List<ChatRoom> = emptyList(),
    @ProtoNumber(2)
    var hasNext: Boolean = false,
)

@Serializable
@JsExport
class GetChatRoomResponse(
    @ProtoNumber(1)
    var room: ChatRoom? = null,
)

@Serializable
@JsExport
class GetChatRoomMessagesResponse(
    @ProtoNumber(1)
    var messages: List<ChatRoomMessage> = emptyList(),
    @ProtoNumber(2)
    var hasNext: Boolean = false,
)

@Serializable
@JsExport
class GetUnreadChatRoomCountResponse(
    @ProtoNumber(1)
    var activeRoomCount: Long = 0,
    @ProtoNumber(2)
    var requestedRoomCount: Long = 0,
    @ProtoNumber(3)
    var mutedRoomCount: Long = 0,
)

@Serializable
@JsExport
class SendMessageToRoomResponse(
    @ProtoNumber(1)
    var message: ChatRoomMessage? = null,
)

@Serializable
@JsExport
class SendDirectMessageResponse(
    @ProtoNumber(1)
    var message: ChatRoomMessage? = null,
)

@Serializable
@JsExport
class SendGroupMessageResponse(
    @ProtoNumber(1)
    var message: ChatRoomMessage? = null,
)
