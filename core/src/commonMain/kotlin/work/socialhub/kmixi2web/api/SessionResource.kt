package work.socialhub.kmixi2web.api

import work.socialhub.kmixi2web.api.request.GetSessionRequest
import work.socialhub.kmixi2web.api.request.SwitchPersonaRequest
import work.socialhub.kmixi2web.api.response.SessionResponse
import work.socialhub.kmixi2web.api.response.SwitchPersonaResponse
import work.socialhub.kmixi2web.entity.share.Response
import kotlin.js.JsExport

@JsExport
interface SessionResource {
    suspend fun getSession(
        request: GetSessionRequest = GetSessionRequest(),
    ): Response<SessionResponse>

    @JsExport.Ignore
    fun getSessionBlocking(
        request: GetSessionRequest = GetSessionRequest(),
    ): Response<SessionResponse>

    suspend fun switchPersona(
        request: SwitchPersonaRequest,
    ): Response<SwitchPersonaResponse>

    @JsExport.Ignore
    fun switchPersonaBlocking(
        request: SwitchPersonaRequest,
    ): Response<SwitchPersonaResponse>
}
