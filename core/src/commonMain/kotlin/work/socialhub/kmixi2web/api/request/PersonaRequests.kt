package work.socialhub.kmixi2web.api.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import work.socialhub.kmixi2web.entity.StatusIcon
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

@Serializable
@JsExport
class GetProfileRequest(
    @ProtoNumber(1)
    var personaId: String,
)

@Serializable
@JsExport
class GetProfileByNameRequest(
    @ProtoNumber(1)
    var name: String,
)

@Serializable
@JsExport
class UpdateProfileRequest(
    @ProtoNumber(1)
    var displayName: String? = null,
    @ProtoNumber(2)
    var profileText: String? = null,
    @ProtoNumber(3)
    var statusIcon: StatusIcon? = null,
    @ProtoNumber(4)
    var statusText: String? = null,
    @ProtoNumber(5)
    var link: String? = null,
)
