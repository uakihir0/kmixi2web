package work.socialhub.kmixi2web.api.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

@Serializable
@JsExport
class GetSessionRequest

@Serializable
@JsExport
class SwitchPersonaRequest(
    @ProtoNumber(1)
    var personaId: String,
)
