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

/**
 * mixi2 community, the closest concept to a channel or a group.
 * Event schedules and posting constraints are not exposed yet.
 */
@Serializable
@JsExport
class Community(
    @ProtoNumber(1)
    var communityId: String = "",
    @ProtoNumber(2)
    var name: String = "",
    @ProtoNumber(3)
    var purpose: String = "",
    @ProtoNumber(4)
    var accessLevel: CommunityAccessLevel = CommunityAccessLevel.PUBLIC,
    @ProtoNumber(5)
    var admins: List<CommunityMember> = emptyList(),
    @ProtoNumber(7)
    var myself: CommunityMember? = null,
    @ProtoNumber(8)
    var countOfMembers: Long = 0,
    @ProtoNumber(9)
    var createdAt: Timestamp? = null,
    @ProtoNumber(10)
    var isArchived: Boolean = false,
    @ProtoNumber(11)
    var coverImage: Media? = null,
    @ProtoNumber(12)
    var members: List<CommunityMember> = emptyList(),
    @ProtoNumber(13)
    var type: CommunityType = CommunityType.TOPIC,
    @ProtoNumber(14)
    var parent: CommunitySummary? = null,
    @ProtoNumber(15)
    var children: List<CommunitySummary> = emptyList(),
    @ProtoNumber(20)
    var tags: List<String> = emptyList(),
    @ProtoNumber(21)
    var visibility: CommunityVisibility = CommunityVisibility.UNSPECIFIED,
    @ProtoNumber(22)
    var creatorId: String? = null,
    @ProtoNumber(23)
    var creator: Persona? = null,
)

@Serializable
@JsExport
class CommunityMember(
    @ProtoNumber(1)
    var communityId: String = "",
    @ProtoNumber(2)
    var persona: Persona? = null,
    @ProtoNumber(3)
    var status: CommunityMemberStatus = CommunityMemberStatus.PARTICIPATING,
    @ProtoNumber(4)
    var isAdmin: Boolean = false,
    @ProtoNumber(5)
    var isCreator: Boolean = false,
    @ProtoNumber(6)
    var createdAt: Timestamp? = null,
    @ProtoNumber(7)
    var personaId: String = "",
)

/**
 * Membership status. The values are not contiguous — `2` is unused — so they
 * are serialized by code instead of by enum ordinal.
 */
@Serializable(with = CommunityMemberStatusSerializer::class)
@JsExport
enum class CommunityMemberStatus(
    val code: Int,
) {
    PARTICIPATING(0),
    WAITING_FOR_APPROVAL(1),
    EXCLUDED(3),
    ;

    companion object {
        fun fromCode(code: Int): CommunityMemberStatus {
            return entries.firstOrNull { it.code == code } ?: PARTICIPATING
        }
    }
}

internal object CommunityMemberStatusSerializer :
    KSerializer<CommunityMemberStatus> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "work.socialhub.kmixi2web.entity.CommunityMemberStatus",
            PrimitiveKind.INT,
        )

    override fun serialize(
        encoder: Encoder,
        value: CommunityMemberStatus,
    ) {
        encoder.encodeInt(value.code)
    }

    override fun deserialize(
        decoder: Decoder,
    ): CommunityMemberStatus {
        return CommunityMemberStatus.fromCode(decoder.decodeInt())
    }
}

@Serializable
@JsExport
class CommunitySummary(
    @ProtoNumber(1)
    var communityId: String = "",
    @ProtoNumber(2)
    var name: String = "",
    @ProtoNumber(3)
    var accessLevel: CommunityAccessLevel = CommunityAccessLevel.PUBLIC,
    @ProtoNumber(4)
    var isArchived: Boolean = false,
    @ProtoNumber(6)
    var coverImage: Media? = null,
    @ProtoNumber(7)
    var type: CommunityType = CommunityType.TOPIC,
    @ProtoNumber(9)
    var parent: CommunitySummary? = null,
    @ProtoNumber(10)
    var postConstraint: Int = 0,
    @ProtoNumber(11)
    var creatorId: String? = null,
)

@Serializable
@JsExport
class CommunityAggregationPost(
    @ProtoNumber(1)
    var communityId: String = "",
    @ProtoNumber(2)
    var post: Post? = null,
    @ProtoNumber(3)
    var aggregationCount: Long = 0,
    @ProtoNumber(4)
    var hasMore: Boolean = false,
    @ProtoNumber(5)
    var community: CommunitySummary? = null,
    @ProtoNumber(6)
    var untilCursor: String = "",
    @ProtoNumber(7)
    var endCursor: String = "",
)
