package work.socialhub.kmixi2web.internal.api

import work.socialhub.kmixi2web.api.ModerationResource
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
import work.socialhub.kmixi2web.internal.MercuryClient
import work.socialhub.kmixi2web.util.toBlocking

internal class ModerationResourceImpl(
    private val client: MercuryClient,
) : ModerationResource {
    override suspend fun blockPersona(
        request: MakePersonaBlockRequest,
    ): Response<MakePersonaBlockResponse> {
        return client.call(
            "MakePersonaBlock",
            request,
            MakePersonaBlockRequest.serializer(),
            MakePersonaBlockResponse.serializer(),
        )
    }

    override fun blockPersonaBlocking(
        request: MakePersonaBlockRequest,
    ) = toBlocking { blockPersona(request) }

    override suspend fun unblockPersona(
        request: MakePersonaUnblockRequest,
    ): Response<MakePersonaUnblockResponse> {
        return client.call(
            "MakePersonaUnblock",
            request,
            MakePersonaUnblockRequest.serializer(),
            MakePersonaUnblockResponse.serializer(),
        )
    }

    override fun unblockPersonaBlocking(
        request: MakePersonaUnblockRequest,
    ) = toBlocking { unblockPersona(request) }

    override suspend fun mutePersona(
        request: MakePersonaMuteRequest,
    ): Response<MakePersonaMuteResponse> {
        return client.call(
            "MakePersonaMute",
            request,
            MakePersonaMuteRequest.serializer(),
            MakePersonaMuteResponse.serializer(),
        )
    }

    override fun mutePersonaBlocking(
        request: MakePersonaMuteRequest,
    ) = toBlocking { mutePersona(request) }

    override suspend fun unmutePersona(
        request: MakePersonaUnmuteRequest,
    ): Response<MakePersonaUnmuteResponse> {
        return client.call(
            "MakePersonaUnmute",
            request,
            MakePersonaUnmuteRequest.serializer(),
            MakePersonaUnmuteResponse.serializer(),
        )
    }

    override fun unmutePersonaBlocking(
        request: MakePersonaUnmuteRequest,
    ) = toBlocking { unmutePersona(request) }

    override suspend fun getBlockPersonas(
        request: GetBlockPersonasRequest,
    ): Response<GetBlockPersonasResponse> {
        return client.call(
            "GetBlockPersonas",
            request,
            GetBlockPersonasRequest.serializer(),
            GetBlockPersonasResponse.serializer(),
        )
    }

    override fun getBlockPersonasBlocking(
        request: GetBlockPersonasRequest,
    ) = toBlocking { getBlockPersonas(request) }

    override suspend fun getMutePersonas(
        request: GetMutePersonasRequest,
    ): Response<GetMutePersonasResponse> {
        return client.call(
            "GetMutePersonas",
            request,
            GetMutePersonasRequest.serializer(),
            GetMutePersonasResponse.serializer(),
        )
    }

    override fun getMutePersonasBlocking(
        request: GetMutePersonasRequest,
    ) = toBlocking { getMutePersonas(request) }

    override suspend fun reportPost(
        request: ReportPostRequest,
    ): Response<ReportPostResponse> {
        return client.call(
            "ReportPost",
            request,
            ReportPostRequest.serializer(),
            ReportPostResponse.serializer(),
        )
    }

    override fun reportPostBlocking(
        request: ReportPostRequest,
    ) = toBlocking { reportPost(request) }

    override suspend fun reportPersona(
        request: ReportPersonaRequest,
    ): Response<ReportPersonaResponse> {
        return client.call(
            "ReportPersona",
            request,
            ReportPersonaRequest.serializer(),
            ReportPersonaResponse.serializer(),
        )
    }

    override fun reportPersonaBlocking(
        request: ReportPersonaRequest,
    ) = toBlocking { reportPersona(request) }
}
