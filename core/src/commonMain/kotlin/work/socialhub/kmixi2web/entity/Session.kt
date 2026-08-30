package work.socialhub.kmixi2web.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

@Serializable
@JsExport
class SessionManagedPersona(
    @ProtoNumber(1)
    var profile: Profile? = null,
    @ProtoNumber(2)
    var isSharedPersona: Boolean = false,
)
