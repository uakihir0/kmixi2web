package work.socialhub.kmixi2web.entity

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

@Serializable(with = NotificationActivityTypeSerializer::class)
@JsExport
enum class NotificationActivityType(
    val code: Int,
) {
    UNKNOWN(0),
    FOLLOW(100),
    FOLLOWING_REQUEST_RECEIVED(101),
    FOLLOWING_REQUEST_APPROVED(102),
    INVITATION_FOLLOW(103),
    LIKE(200),
    REPOST(201),
    QUOTE(202),
    REPLY(204),
    MENTION(205),
    REACTION(206),
    COMMUNITY_JOIN_REQUEST(300),
    COMMUNITY_JOIN_ACCEPT(301),
    COMMUNITY_INVITATION(310),
    COMMUNITY_GRANT_ADMIN_PRIVILEGES(311),
    COMMUNITY_JOIN(312),
    EVENT_JOIN(400),
    EVENT_REMINDER(401),
    ;

    companion object {
        fun fromCode(code: Int): NotificationActivityType {
            return entries.firstOrNull { it.code == code } ?: UNKNOWN
        }
    }
}

internal object NotificationActivityTypeSerializer :
    KSerializer<NotificationActivityType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "work.socialhub.kmixi2web.entity.NotificationActivityType",
            PrimitiveKind.INT,
        )

    override fun serialize(
        encoder: Encoder,
        value: NotificationActivityType,
    ) {
        encoder.encodeInt(value.code)
    }

    override fun deserialize(
        decoder: Decoder,
    ): NotificationActivityType {
        return NotificationActivityType.fromCode(decoder.decodeInt())
    }
}

@Serializable
@JsExport
class Notification(
    @ProtoNumber(1)
    var activityType: NotificationActivityType = NotificationActivityType.UNKNOWN,
    @ProtoNumber(2)
    var createdAt: Timestamp? = null,
    @ProtoNumber(3)
    var timeSeriesId: String = "",
    @ProtoNumber(4)
    var issuerId: String = "",
    @ProtoNumber(5)
    var postId: String? = null,
    @ProtoNumber(6)
    var communityRequestId: String? = null,
    @ProtoNumber(7)
    var communityId: String? = null,
    @ProtoNumber(8)
    var reaction: NotificationReaction? = null,
    @ProtoNumber(9)
    var followingRequestId: String? = null,
)

@Serializable
@JsExport
class NotificationReaction(
    @ProtoNumber(1)
    var stampId: String = "",
    @ProtoNumber(2)
    var imageUrl: String = "",
)
