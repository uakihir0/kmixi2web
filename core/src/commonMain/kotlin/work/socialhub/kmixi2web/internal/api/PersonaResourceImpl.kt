package work.socialhub.kmixi2web.internal.api

import work.socialhub.kmixi2web.api.PersonaResource
import work.socialhub.kmixi2web.api.request.GetPersonaByNameRequest
import work.socialhub.kmixi2web.api.request.GetPersonasRequest
import work.socialhub.kmixi2web.api.response.GetPersonaResponse
import work.socialhub.kmixi2web.api.response.GetPersonasResponse
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
}
