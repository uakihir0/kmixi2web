package work.socialhub.kmixi2web.api.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.Persona
import work.socialhub.kmixi2web.entity.Post
import work.socialhub.kmixi2web.entity.Profile
import kotlin.js.JsExport

@Serializable
@JsExport
class MakePersonaBlockResponse(
    @ProtoNumber(1)
    var profile: Profile? = null,
)

@Serializable
@JsExport
class MakePersonaUnblockResponse(
    @ProtoNumber(1)
    var profile: Profile? = null,
)

@Serializable
@JsExport
class MakePersonaMuteResponse(
    @ProtoNumber(1)
    var persona: Persona? = null,
)

@Serializable
@JsExport
class MakePersonaUnmuteResponse(
    @ProtoNumber(1)
    var persona: Persona? = null,
)

@Serializable
@JsExport
class GetBlockPersonasResponse(
    @ProtoNumber(1)
    var personaIds: List<String> = emptyList(),
)

@Serializable
@JsExport
class GetMutePersonasResponse(
    @ProtoNumber(1)
    var personaIds: List<String> = emptyList(),
)

@Serializable
@JsExport
class ReportPostResponse(
    @ProtoNumber(1)
    var post: Post? = null,
)

@Serializable
@JsExport
class ReportPersonaResponse(
    @ProtoNumber(1)
    var persona: Persona? = null,
)
