package work.socialhub.kmixi2web.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

@Serializable
@JsExport
class Profile(
    @ProtoNumber(1)
    var persona: Persona? = null,
    @ProtoNumber(2)
    var followingCount: Long = 0,
    @ProtoNumber(3)
    var followedCount: Long = 0,
    @ProtoNumber(4)
    var text: String = "",
    @ProtoNumber(5)
    var profileImageUrl: String = "",
    @ProtoNumber(6)
    var link: String = "",
    @ProtoNumber(7)
    var personaConnectivity: PersonaConnectivity? = null,
    @ProtoNumber(8)
    var isMuted: Boolean = false,
    @ProtoNumber(9)
    var postPins: List<ProfilePostPin> = emptyList(),
    @ProtoNumber(10)
    var isBlocking: Boolean = false,
    @ProtoNumber(11)
    var isBlocked: Boolean = false,
    @ProtoNumber(12)
    var isPostNotificationTarget: Boolean = false,
    @ProtoNumber(13)
    var socialMedia: List<ProfileSocialMedia> = emptyList(),
)

@Serializable
@JsExport
class ProfilePostPin(
    @ProtoNumber(1)
    var postPinId: String = "",
    @ProtoNumber(2)
    var postId: String = "",
)

@Serializable
@JsExport
class ProfileSocialMedia(
    @ProtoNumber(1)
    var socialMediaType: Int = 0,
    @ProtoNumber(2)
    var username: String = "",
    @ProtoNumber(3)
    var verified: Boolean = false,
)
