package work.socialhub.kmixi2web.internal.api

import work.socialhub.kmixi2web.api.ReactionResource
import work.socialhub.kmixi2web.api.request.AddStampToPostRequest
import work.socialhub.kmixi2web.api.request.CreateLikeRequest
import work.socialhub.kmixi2web.api.request.DeleteLikeRequest
import work.socialhub.kmixi2web.api.request.GetLikingPersonasRequest
import work.socialhub.kmixi2web.api.request.GetPostStampReactionsRequest
import work.socialhub.kmixi2web.api.request.GetStampsRequest
import work.socialhub.kmixi2web.api.request.RemoveStampFromPostRequest
import work.socialhub.kmixi2web.api.response.AddStampToPostResponse
import work.socialhub.kmixi2web.api.response.CreateLikeResponse
import work.socialhub.kmixi2web.api.response.DeleteLikeResponse
import work.socialhub.kmixi2web.api.response.GetLikingPersonasResponse
import work.socialhub.kmixi2web.api.response.GetPostStampReactionsResponse
import work.socialhub.kmixi2web.api.response.GetStampsResponse
import work.socialhub.kmixi2web.api.response.RemoveStampFromPostResponse
import work.socialhub.kmixi2web.entity.share.Response
import work.socialhub.kmixi2web.internal.MercuryClient
import work.socialhub.kmixi2web.util.toBlocking

internal class ReactionResourceImpl(
    private val client: MercuryClient,
) : ReactionResource {
    override suspend fun getPostStampReactions(
        request: GetPostStampReactionsRequest,
    ): Response<GetPostStampReactionsResponse> {
        return client.call(
            "GetPostStampReactions",
            request,
            GetPostStampReactionsRequest.serializer(),
            GetPostStampReactionsResponse.serializer(),
        )
    }

    override fun getPostStampReactionsBlocking(
        request: GetPostStampReactionsRequest,
    ) = toBlocking { getPostStampReactions(request) }

    override suspend fun getStamps(
        request: GetStampsRequest,
    ): Response<GetStampsResponse> {
        return client.call(
            "GetStamps",
            request,
            GetStampsRequest.serializer(),
            GetStampsResponse.serializer(),
        )
    }

    override fun getStampsBlocking(
        request: GetStampsRequest,
    ) = toBlocking { getStamps(request) }

    override suspend fun addStampToPost(
        request: AddStampToPostRequest,
    ): Response<AddStampToPostResponse> {
        return client.call(
            "AddStampToPost",
            request,
            AddStampToPostRequest.serializer(),
            AddStampToPostResponse.serializer(),
        )
    }

    override fun addStampToPostBlocking(
        request: AddStampToPostRequest,
    ) = toBlocking { addStampToPost(request) }

    override suspend fun removeStampFromPost(
        request: RemoveStampFromPostRequest,
    ): Response<RemoveStampFromPostResponse> {
        return client.call(
            "RemoveStampFromPost",
            request,
            RemoveStampFromPostRequest.serializer(),
            RemoveStampFromPostResponse.serializer(),
        )
    }

    override fun removeStampFromPostBlocking(
        request: RemoveStampFromPostRequest,
    ) = toBlocking { removeStampFromPost(request) }

    override suspend fun createLike(
        request: CreateLikeRequest,
    ): Response<CreateLikeResponse> {
        return client.call(
            "CreateLike",
            request,
            CreateLikeRequest.serializer(),
            CreateLikeResponse.serializer(),
        )
    }

    override fun createLikeBlocking(
        request: CreateLikeRequest,
    ) = toBlocking { createLike(request) }

    override suspend fun deleteLike(
        request: DeleteLikeRequest,
    ): Response<DeleteLikeResponse> {
        return client.call(
            "DeleteLike",
            request,
            DeleteLikeRequest.serializer(),
            DeleteLikeResponse.serializer(),
        )
    }

    override fun deleteLikeBlocking(
        request: DeleteLikeRequest,
    ) = toBlocking { deleteLike(request) }

    override suspend fun getLikingPersonas(
        request: GetLikingPersonasRequest,
    ): Response<GetLikingPersonasResponse> {
        return client.call(
            "GetLikingPersonas",
            request,
            GetLikingPersonasRequest.serializer(),
            GetLikingPersonasResponse.serializer(),
        )
    }

    override fun getLikingPersonasBlocking(
        request: GetLikingPersonasRequest,
    ) = toBlocking { getLikingPersonas(request) }
}
