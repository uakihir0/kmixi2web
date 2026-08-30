package work.socialhub.kmixi2web.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

@Serializable
@JsExport
class Following(
    @ProtoNumber(1)
    var personaId: String = "",
    @ProtoNumber(2)
    var createdAt: Timestamp? = null,
    @ProtoNumber(3)
    var persona: Persona? = null,
    @ProtoNumber(4)
    var connectivity: PersonaConnectivity? = null,
)

@Serializable
@JsExport
class Follower(
    @ProtoNumber(1)
    var personaId: String = "",
    @ProtoNumber(2)
    var createdAt: Timestamp? = null,
    @ProtoNumber(3)
    var persona: Persona? = null,
    @ProtoNumber(4)
    var connectivity: PersonaConnectivity? = null,
)

@Serializable
@JsExport
class FollowingRequest(
    @ProtoNumber(1)
    var requestId: String = "",
    @ProtoNumber(2)
    var senderId: String = "",
    @ProtoNumber(3)
    var receiverId: String = "",
    @ProtoNumber(4)
    var createdAt: Timestamp? = null,
    @ProtoNumber(5)
    var status: Int = 0,
)
