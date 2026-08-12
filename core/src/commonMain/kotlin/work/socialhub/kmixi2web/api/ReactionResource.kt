package work.socialhub.kmixi2web.api

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
import kotlin.js.JsExport

@JsExport
interface ReactionResource {
    suspend fun getPostStampReactions(
        request: GetPostStampReactionsRequest,
    ): Response<GetPostStampReactionsResponse>

    @JsExport.Ignore
    fun getPostStampReactionsBlocking(
        request: GetPostStampReactionsRequest,
    ): Response<GetPostStampReactionsResponse>

    suspend fun getStamps(
        request: GetStampsRequest,
    ): Response<GetStampsResponse>

    @JsExport.Ignore
    fun getStampsBlocking(
        request: GetStampsRequest,
    ): Response<GetStampsResponse>

    suspend fun addStampToPost(
        request: AddStampToPostRequest,
    ): Response<AddStampToPostResponse>

    @JsExport.Ignore
    fun addStampToPostBlocking(
        request: AddStampToPostRequest,
    ): Response<AddStampToPostResponse>

    suspend fun removeStampFromPost(
        request: RemoveStampFromPostRequest,
    ): Response<RemoveStampFromPostResponse>

    @JsExport.Ignore
    fun removeStampFromPostBlocking(
        request: RemoveStampFromPostRequest,
    ): Response<RemoveStampFromPostResponse>

    suspend fun createLike(
        request: CreateLikeRequest,
    ): Response<CreateLikeResponse>

    @JsExport.Ignore
    fun createLikeBlocking(
        request: CreateLikeRequest,
    ): Response<CreateLikeResponse>

    suspend fun deleteLike(
        request: DeleteLikeRequest,
    ): Response<DeleteLikeResponse>

    @JsExport.Ignore
    fun deleteLikeBlocking(
        request: DeleteLikeRequest,
    ): Response<DeleteLikeResponse>

    suspend fun getLikingPersonas(
        request: GetLikingPersonasRequest,
    ): Response<GetLikingPersonasResponse>

    @JsExport.Ignore
    fun getLikingPersonasBlocking(
        request: GetLikingPersonasRequest,
    ): Response<GetLikingPersonasResponse>
}
