package work.socialhub.kmixi2web.api

import work.socialhub.kmixi2web.entity.share.Response
import kotlin.js.JsExport

@JsExport
interface RawResource {
    suspend fun call(
        rpcName: String,
        requestBody: ByteArray,
    ): Response<ByteArray>

    @JsExport.Ignore
    fun callBlocking(
        rpcName: String,
        requestBody: ByteArray,
    ): Response<ByteArray>
}
