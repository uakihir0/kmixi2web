package work.socialhub.kmixi2web.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.js.JsExport

@Serializable
@JsExport
class Timestamp(
    @ProtoNumber(1)
    var seconds: Long = 0,
    @ProtoNumber(2)
    var nanos: Int = 0,
)
