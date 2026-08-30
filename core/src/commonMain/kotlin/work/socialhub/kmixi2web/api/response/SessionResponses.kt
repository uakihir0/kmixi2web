package work.socialhub.kmixi2web.api.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.SessionManagedPersona
import kotlin.js.JsExport

@Serializable
@JsExport
class SessionResponse(
    @ProtoNumber(1)
    var sessionManagedPersonas: List<SessionManagedPersona> = emptyList(),
    @ProtoNumber(2)
    var activePersonaId: String? = null,
    @ProtoNumber(3)
    var isAccountFrozen: Boolean = false,
)

@Serializable
@JsExport
class SwitchPersonaResponse
