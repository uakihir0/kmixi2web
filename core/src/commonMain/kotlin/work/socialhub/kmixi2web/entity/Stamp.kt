package work.socialhub.kmixi2web.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

@Serializable
@JsExport
class Stamp(
    @ProtoNumber(1)
    var stampId: String = "",
    @ProtoNumber(2)
    var url: String = "",
)

@Serializable
@JsExport
class PostStamp(
    @ProtoNumber(1)
    var stamp: Stamp? = null,
    @ProtoNumber(2)
    var count: Long = 0,
)

@Serializable
@JsExport
class StampReaction(
    @ProtoNumber(1)
    var stampId: String = "",
    @ProtoNumber(2)
    var persona: Persona? = null,
)

@Serializable
@JsExport
class OfficialStamp(
    @ProtoNumber(1)
    var stampId: String = "",
    @ProtoNumber(2)
    var index: Int = 0,
    @ProtoNumber(3)
    var searchTags: List<String> = emptyList(),
    @ProtoNumber(4)
    var url: String = "",
)

@Serializable
@JsExport
class OfficialStampSet(
    @ProtoNumber(1)
    var name: String = "",
    @ProtoNumber(2)
    var spriteUrl: String = "",
    @ProtoNumber(3)
    var stamps: List<OfficialStamp> = emptyList(),
    @ProtoNumber(4)
    var stampSetId: String = "",
    @ProtoNumber(5)
    var startAt: Timestamp? = null,
    @ProtoNumber(6)
    var endAt: Timestamp? = null,
    @ProtoNumber(7)
    var stampSetType: Int = 0,
)

@Serializable
@JsExport
class CommunityStamp(
    @ProtoNumber(1)
    var stampId: String = "",
    @ProtoNumber(2)
    var url: String = "",
    @ProtoNumber(3)
    var searchTags: List<String> = emptyList(),
)

@Serializable
@JsExport
class CommunityStampSet(
    @ProtoNumber(1)
    var communityId: String = "",
    @ProtoNumber(2)
    var stamps: List<CommunityStamp> = emptyList(),
)

@Serializable
@JsExport
class PersonaObtainedStampSet(
    @ProtoNumber(1)
    var name: String = "",
    @ProtoNumber(2)
    var spriteUrl: String = "",
    @ProtoNumber(3)
    var stamps: List<OfficialStamp> = emptyList(),
    @ProtoNumber(4)
    var obtainedStamps: List<OfficialStamp> = emptyList(),
    @ProtoNumber(5)
    var stampSetId: String = "",
    @ProtoNumber(6)
    var startAt: Timestamp? = null,
    @ProtoNumber(7)
    var endAt: Timestamp? = null,
)
