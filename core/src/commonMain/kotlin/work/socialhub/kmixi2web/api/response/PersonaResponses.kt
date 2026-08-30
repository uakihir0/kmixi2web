package work.socialhub.kmixi2web.api.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.Persona
import work.socialhub.kmixi2web.entity.Profile
import kotlin.js.JsExport

@Serializable
@JsExport
class GetPersonasResponse(
    @ProtoNumber(1)
    var personas: List<Persona> = emptyList(),
)

@Serializable
@JsExport
class GetPersonaResponse(
    @ProtoNumber(1)
    var persona: Persona? = null,
)

@Serializable
@JsExport
class GetProfileResponse(
    @ProtoNumber(1)
    var profile: Profile? = null,
)

@Serializable
@JsExport
class UpdateProfileResponse(
    @ProtoNumber(1)
    var profile: Profile? = null,
)
