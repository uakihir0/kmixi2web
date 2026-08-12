package work.socialhub.kmixi2web.api.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

@Serializable
@JsExport
class GetPersonasRequest(
    @ProtoNumber(1)
    var personaIds: List<String> = emptyList(),
)

@Serializable
@JsExport
class GetPersonaByNameRequest(
    @ProtoNumber(1)
    var name: String,
)
