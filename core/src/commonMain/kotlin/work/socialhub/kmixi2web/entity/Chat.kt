package work.socialhub.kmixi2web.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

/**
 * Direct or group chat room. A room with [isGroup] `false` is a one-to-one
 * conversation, so its counterpart is the member other than the active persona.
 */
@Serializable
@JsExport
class ChatRoom(
    @ProtoNumber(1)
    var roomId: String = "",
    @ProtoNumber(2)
    var isGroup: Boolean = false,
    @ProtoNumber(3)
    var title: String? = null,
    @ProtoNumber(4)
    var members: List<ChatRoomMember> = emptyList(),
    @ProtoNumber(5)
    var createdAt: Timestamp? = null,
    /** Most recent message, used for room previews. */
    @ProtoNumber(6)
    var message: ChatRoomMessage? = null,
    @ProtoNumber(7)
    var status: ChatRoomStatus = ChatRoomStatus.UNKNOWN,
    @ProtoNumber(8)
    var isMute: Boolean = false,
    @ProtoNumber(9)
    var isInvisible: Boolean = false,
)

@Serializable
@JsExport
class ChatRoomMember(
    @ProtoNumber(1)
    var personaId: String = "",
    /** Last message the member has read; `null` when nothing was read yet. */
    @ProtoNumber(2)
    var readMessageId: String? = null,
)

@Serializable
@JsExport
class ChatRoomMessage(
    @ProtoNumber(1)
    var roomId: String = "",
    @ProtoNumber(2)
    var messageId: String = "",
    @ProtoNumber(3)
    var personaId: String = "",
    @ProtoNumber(4)
    var messageType: ChatRoomMessageType = ChatRoomMessageType.MESSAGE,
    @ProtoNumber(5)
    var messageTargetId: String? = null,
    @ProtoNumber(6)
    var text: String? = null,
    @ProtoNumber(8)
    var createdAt: Timestamp? = null,
    @ProtoNumber(9)
    var media: List<Media> = emptyList(),
    /** Shared post when the message quotes one. */
    @ProtoNumber(10)
    var post: Post? = null,
)
