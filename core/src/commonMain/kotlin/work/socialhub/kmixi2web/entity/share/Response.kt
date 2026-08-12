package work.socialhub.kmixi2web.entity.share

import kotlin.js.JsExport

@JsExport
class Response<T>(
    var data: T,
    var status: Int,
    var bytes: ByteArray,
)
