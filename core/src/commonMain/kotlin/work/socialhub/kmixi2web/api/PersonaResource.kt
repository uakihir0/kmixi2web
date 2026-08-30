package work.socialhub.kmixi2web.api

import work.socialhub.kmixi2web.api.request.GetPersonaByNameRequest
import work.socialhub.kmixi2web.api.request.GetPersonasRequest
import work.socialhub.kmixi2web.api.request.GetProfileByNameRequest
import work.socialhub.kmixi2web.api.request.GetProfileRequest
import work.socialhub.kmixi2web.api.request.UpdateProfileRequest
import work.socialhub.kmixi2web.api.response.GetPersonaResponse
import work.socialhub.kmixi2web.api.response.GetPersonasResponse
import work.socialhub.kmixi2web.api.response.GetProfileResponse
import work.socialhub.kmixi2web.api.response.UpdateProfileResponse
import work.socialhub.kmixi2web.entity.share.Response
import kotlin.js.JsExport

@JsExport
interface PersonaResource {
    suspend fun getPersonas(request: GetPersonasRequest): Response<GetPersonasResponse>

    @JsExport.Ignore
    fun getPersonasBlocking(request: GetPersonasRequest): Response<GetPersonasResponse>

    suspend fun getPersonaByName(
        request: GetPersonaByNameRequest,
    ): Response<GetPersonaResponse>

    @JsExport.Ignore
    fun getPersonaByNameBlocking(
        request: GetPersonaByNameRequest,
    ): Response<GetPersonaResponse>

    suspend fun getProfile(
        request: GetProfileRequest,
    ): Response<GetProfileResponse>

    @JsExport.Ignore
    fun getProfileBlocking(
        request: GetProfileRequest,
    ): Response<GetProfileResponse>

    suspend fun getProfileByName(
        request: GetProfileByNameRequest,
    ): Response<GetProfileResponse>

    @JsExport.Ignore
    fun getProfileByNameBlocking(
        request: GetProfileByNameRequest,
    ): Response<GetProfileResponse>

    suspend fun updateProfile(
        request: UpdateProfileRequest,
    ): Response<UpdateProfileResponse>

    @JsExport.Ignore
    fun updateProfileBlocking(
        request: UpdateProfileRequest,
    ): Response<UpdateProfileResponse>
}
