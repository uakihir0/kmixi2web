package work.socialhub.kmixi2web.api.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlinx.serialization.protobuf.ProtoPacked
import work.socialhub.kmixi2web.entity.NotificationActivityType
import kotlin.js.JsExport

@Serializable
@JsExport
class GetNotificationsRequest(
    @ProtoNumber(1)
    var activityType: NotificationActivityType? = null,
    @ProtoNumber(2)
    var limit: Int? = null,
    @ProtoNumber(3)
    var untilTimeSeriesId: String? = null,
    @ProtoNumber(4)
    var endTimeSeriesId: String? = null,
    @ProtoNumber(5)
    @ProtoPacked
    var activityTypes: List<NotificationActivityType> = emptyList(),
)

@Serializable
@JsExport
class GetBadgeCountRequest

@Serializable
@JsExport
class MarkNotificationAsReadRequest(
    @ProtoNumber(1)
    var timeSeriesId: String,
)

@Serializable
@JsExport
class MarkNotificationsAsReadBeforeTimeRequest(
    @ProtoNumber(1)
    var latestTimeSeriesId: String,
)
