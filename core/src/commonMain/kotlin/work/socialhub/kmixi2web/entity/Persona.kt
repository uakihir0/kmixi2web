package work.socialhub.kmixi2web.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

@Serializable
@JsExport
class StatusIcon(
    @ProtoNumber(3)
    var type: Int = 0,
    @ProtoNumber(4)
    var icon: String = "",
)

@Serializable
@JsExport
class Persona(
    @ProtoNumber(1)
    var personaId: String = "",
    @ProtoNumber(2)
    var name: String = "",
    @ProtoNumber(3)
    var displayName: String = "",
    @ProtoNumber(4)
    var avatarUrl: String = "",
    @ProtoNumber(5)
    var isDeleted: Boolean = false,
    @ProtoNumber(6)
    var following: Boolean = false,
    @ProtoNumber(7)
    var followed: Boolean = false,
    @ProtoNumber(8)
    var profileText: String = "",
    @ProtoNumber(9)
    var profileImageUrl: String = "",
    @ProtoNumber(10)
    var statusIcon: StatusIcon? = null,
    @ProtoNumber(11)
    var statusText: String? = null,
    @ProtoNumber(12)
    var isPersonaFrozen: Boolean = false,
    @ProtoNumber(13)
    var verificationType: Int = 0,
    @ProtoNumber(14)
    var isBlocking: Boolean = false,
    @ProtoNumber(15)
    var visibility: Int = 0,
    @ProtoNumber(16)
    var followingStatus: Int = 0,
    @ProtoNumber(17)
    var createdAt: Timestamp? = null,
    @ProtoNumber(18)
    var personaType: Int = 0,
)

@Serializable
@JsExport
class PersonaName(
    @ProtoNumber(1)
    var personaId: String = "",
    @ProtoNumber(2)
    var name: String = "",
)

@Serializable
@JsExport
class PersonaConnectivity(
    @ProtoNumber(1)
    var following: Boolean = false,
    @ProtoNumber(2)
    var countOfFollowingAmongMyFollowings: Long = 0,
    @ProtoNumber(3)
    var followingPersonasAmongMyFollowings: List<Persona> = emptyList(),
    @ProtoNumber(4)
    var followed: Boolean = false,
    @ProtoNumber(5)
    var followingStatus: Int = 0,
)

@Serializable
@JsExport
class PersonaWithConnectivity(
    @ProtoNumber(1)
    var persona: Persona? = null,
    @ProtoNumber(2)
    var connectivity: PersonaConnectivity? = null,
)
