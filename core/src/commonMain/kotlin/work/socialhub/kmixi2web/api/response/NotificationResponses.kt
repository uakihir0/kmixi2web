package work.socialhub.kmixi2web.api.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlinx.serialization.protobuf.ProtoPacked
import work.socialhub.kmixi2web.entity.Notification
import kotlin.js.JsExport

@Serializable
@JsExport
class GetNotificationsResponse(
    @ProtoNumber(1)
    var notifications: List<Notification> = emptyList(),
    @ProtoNumber(2)
    var hasNext: Boolean = false,
)

@Serializable
@JsExport
class GetBadgeCountResponse(
    @ProtoNumber(1)
    var accountUnreadCount: Long = 0,
    @ProtoNumber(2)
    var personaUnreadNotificationCount: Long = 0,
    @ProtoNumber(3)
    var personaUnreadActiveRoomCount: Long = 0,
    @ProtoNumber(4)
    var personaUnreadRequestedRoomCount: Long = 0,
    @ProtoNumber(5)
    var personaUnreadMutedRoomCount: Long = 0,
    @ProtoNumber(6)
    @ProtoPacked
    var unreadList: List<Boolean> = emptyList(),
)

@Serializable
@JsExport
class MarkNotificationAsReadResponse

@Serializable
@JsExport
class MarkNotificationsAsReadResponse
