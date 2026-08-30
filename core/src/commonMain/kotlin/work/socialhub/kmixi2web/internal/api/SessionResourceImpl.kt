package work.socialhub.kmixi2web.internal.api

import work.socialhub.kmixi2web.api.SessionResource
import work.socialhub.kmixi2web.api.request.GetSessionRequest
import work.socialhub.kmixi2web.api.request.SwitchPersonaRequest
import work.socialhub.kmixi2web.api.response.SessionResponse
import work.socialhub.kmixi2web.api.response.SwitchPersonaResponse
import work.socialhub.kmixi2web.entity.share.Response
import work.socialhub.kmixi2web.internal.MercuryClient
import work.socialhub.kmixi2web.util.toBlocking

internal class SessionResourceImpl(
    private val client: MercuryClient,
) : SessionResource {
    override suspend fun getSession(
        request: GetSessionRequest,
    ): Response<SessionResponse> {
        return client.call(
            "GetSession",
            request,
            GetSessionRequest.serializer(),
            SessionResponse.serializer(),
        )
    }

    override fun getSessionBlocking(
        request: GetSessionRequest,
    ) = toBlocking { getSession(request) }

    override suspend fun switchPersona(
        request: SwitchPersonaRequest,
    ): Response<SwitchPersonaResponse> {
        return client.call(
            "SwitchPersona",
            request,
            SwitchPersonaRequest.serializer(),
            SwitchPersonaResponse.serializer(),
        )
    }

    override fun switchPersonaBlocking(
        request: SwitchPersonaRequest,
    ) = toBlocking { switchPersona(request) }
}
