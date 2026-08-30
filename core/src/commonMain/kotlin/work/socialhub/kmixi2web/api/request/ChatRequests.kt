package work.socialhub.kmixi2web.api.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

@Serializable
@JsExport
class GetChatRoomsRequest(
    @ProtoNumber(1)
    var limit: Int? = null,
    @ProtoNumber(2)
    var untilMessageId: String? = null,
    @ProtoNumber(3)
    var sinceMessageId: String? = null,
)

@Serializable
@JsExport
class GetChatRoomRequest(
    @ProtoNumber(1)
    var roomId: String,
)

@Serializable
@JsExport
class GetChatRoomMessagesRequest(
    @ProtoNumber(1)
    var roomId: String,
    @ProtoNumber(2)
    var limit: Int? = null,
    @ProtoNumber(3)
    var untilMessageId: String? = null,
    @ProtoNumber(4)
    var sinceMessageId: String? = null,
)

@Serializable
@JsExport
class GetUnreadChatRoomCountRequest

@Serializable
@JsExport
class SendMessageToRoomRequest(
    @ProtoNumber(1)
    var roomId: String,
    @ProtoNumber(2)
    var text: String? = null,
    @ProtoNumber(3)
    var mediaIds: List<String> = emptyList(),
)

@Serializable
@JsExport
class SendDirectMessageRequest(
    @ProtoNumber(1)
    var receiverId: String,
    @ProtoNumber(2)
    var text: String? = null,
    @ProtoNumber(3)
    var mediaIds: List<String> = emptyList(),
    @ProtoNumber(4)
    var postId: String? = null,
)

@Serializable
@JsExport
class SendGroupMessageRequest(
    @ProtoNumber(1)
    var receiverIds: List<String> = emptyList(),
    @ProtoNumber(2)
    var text: String? = null,
    @ProtoNumber(3)
    var mediaIds: List<String> = emptyList(),
)
