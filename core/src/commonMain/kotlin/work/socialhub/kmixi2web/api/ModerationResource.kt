package work.socialhub.kmixi2web.api

import work.socialhub.kmixi2web.api.request.GetBlockPersonasRequest
import work.socialhub.kmixi2web.api.request.GetMutePersonasRequest
import work.socialhub.kmixi2web.api.request.MakePersonaBlockRequest
import work.socialhub.kmixi2web.api.request.MakePersonaMuteRequest
import work.socialhub.kmixi2web.api.request.MakePersonaUnblockRequest
import work.socialhub.kmixi2web.api.request.MakePersonaUnmuteRequest
import work.socialhub.kmixi2web.api.request.ReportPersonaRequest
import work.socialhub.kmixi2web.api.request.ReportPostRequest
import work.socialhub.kmixi2web.api.response.GetBlockPersonasResponse
import work.socialhub.kmixi2web.api.response.GetMutePersonasResponse
import work.socialhub.kmixi2web.api.response.MakePersonaBlockResponse
import work.socialhub.kmixi2web.api.response.MakePersonaMuteResponse
import work.socialhub.kmixi2web.api.response.MakePersonaUnblockResponse
import work.socialhub.kmixi2web.api.response.MakePersonaUnmuteResponse
import work.socialhub.kmixi2web.api.response.ReportPersonaResponse
import work.socialhub.kmixi2web.api.response.ReportPostResponse
import work.socialhub.kmixi2web.entity.share.Response
import kotlin.js.JsExport

@JsExport
interface ModerationResource {
    suspend fun blockPersona(
        request: MakePersonaBlockRequest,
    ): Response<MakePersonaBlockResponse>

    @JsExport.Ignore
    fun blockPersonaBlocking(
        request: MakePersonaBlockRequest,
    ): Response<MakePersonaBlockResponse>

    suspend fun unblockPersona(
        request: MakePersonaUnblockRequest,
    ): Response<MakePersonaUnblockResponse>

    @JsExport.Ignore
    fun unblockPersonaBlocking(
        request: MakePersonaUnblockRequest,
    ): Response<MakePersonaUnblockResponse>

    suspend fun mutePersona(
        request: MakePersonaMuteRequest,
    ): Response<MakePersonaMuteResponse>

    @JsExport.Ignore
    fun mutePersonaBlocking(
        request: MakePersonaMuteRequest,
    ): Response<MakePersonaMuteResponse>

    suspend fun unmutePersona(
        request: MakePersonaUnmuteRequest,
    ): Response<MakePersonaUnmuteResponse>

    @JsExport.Ignore
    fun unmutePersonaBlocking(
        request: MakePersonaUnmuteRequest,
    ): Response<MakePersonaUnmuteResponse>

    suspend fun getBlockPersonas(
        request: GetBlockPersonasRequest = GetBlockPersonasRequest(),
    ): Response<GetBlockPersonasResponse>

    @JsExport.Ignore
    fun getBlockPersonasBlocking(
        request: GetBlockPersonasRequest = GetBlockPersonasRequest(),
    ): Response<GetBlockPersonasResponse>

    suspend fun getMutePersonas(
        request: GetMutePersonasRequest = GetMutePersonasRequest(),
    ): Response<GetMutePersonasResponse>

    @JsExport.Ignore
    fun getMutePersonasBlocking(
        request: GetMutePersonasRequest = GetMutePersonasRequest(),
    ): Response<GetMutePersonasResponse>

    suspend fun reportPost(
        request: ReportPostRequest,
    ): Response<ReportPostResponse>

    @JsExport.Ignore
    fun reportPostBlocking(
        request: ReportPostRequest,
    ): Response<ReportPostResponse>

    suspend fun reportPersona(
        request: ReportPersonaRequest,
    ): Response<ReportPersonaResponse>

    @JsExport.Ignore
    fun reportPersonaBlocking(
        request: ReportPersonaRequest,
    ): Response<ReportPersonaResponse>
}
