package work.socialhub.kmixi2web.entity.share

import kotlin.js.JsExport

@JsExport
class ResponseUnit(
    var status: Int,
    var bytes: ByteArray,
)
