package work.socialhub.kmixi2web.internal.api

import work.socialhub.kmixi2web.api.PersonaResource
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
import work.socialhub.kmixi2web.internal.MercuryClient
import work.socialhub.kmixi2web.util.toBlocking

internal class PersonaResourceImpl(
    private val client: MercuryClient,
) : PersonaResource {
    override suspend fun getPersonas(
        request: GetPersonasRequest,
    ): Response<GetPersonasResponse> {
        return client.call(
            "GetPersonas",
            request,
            GetPersonasRequest.serializer(),
            GetPersonasResponse.serializer(),
        )
    }

    override fun getPersonasBlocking(
        request: GetPersonasRequest,
    ) = toBlocking { getPersonas(request) }

    override suspend fun getPersonaByName(
        request: GetPersonaByNameRequest,
    ): Response<GetPersonaResponse> {
        return client.call(
            "GetPersonaByName",
            request,
            GetPersonaByNameRequest.serializer(),
            GetPersonaResponse.serializer(),
        )
    }

    override fun getPersonaByNameBlocking(
        request: GetPersonaByNameRequest,
    ) = toBlocking { getPersonaByName(request) }

    override suspend fun getProfile(
        request: GetProfileRequest,
    ): Response<GetProfileResponse> {
        return client.call(
            "GetProfile",
            request,
            GetProfileRequest.serializer(),
            GetProfileResponse.serializer(),
        )
    }

    override fun getProfileBlocking(
        request: GetProfileRequest,
    ) = toBlocking { getProfile(request) }

    override suspend fun getProfileByName(
        request: GetProfileByNameRequest,
    ): Response<GetProfileResponse> {
        return client.call(
            "GetProfileByName",
            request,
            GetProfileByNameRequest.serializer(),
            GetProfileResponse.serializer(),
        )
    }

    override fun getProfileByNameBlocking(
        request: GetProfileByNameRequest,
    ) = toBlocking { getProfileByName(request) }

    override suspend fun updateProfile(
        request: UpdateProfileRequest,
    ): Response<UpdateProfileResponse> {
        return client.call(
            "UpdateProfile",
            request,
            UpdateProfileRequest.serializer(),
            UpdateProfileResponse.serializer(),
        )
    }

    override fun updateProfileBlocking(
        request: UpdateProfileRequest,
    ) = toBlocking { updateProfile(request) }
}
