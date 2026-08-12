package work.socialhub.kmixi2web.internal.api

import work.socialhub.kmixi2web.api.RawResource
import work.socialhub.kmixi2web.internal.MercuryClient
import work.socialhub.kmixi2web.util.toBlocking

internal class RawResourceImpl(
    private val client: MercuryClient,
) : RawResource {
    override suspend fun call(
        rpcName: String,
        requestBody: ByteArray,
    ) = client.callRaw(rpcName, requestBody)

    override fun callBlocking(
        rpcName: String,
        requestBody: ByteArray,
    ) = toBlocking { call(rpcName, requestBody) }
}
