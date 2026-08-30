package work.socialhub.kmixi2web.api

import work.socialhub.kmixi2web.api.request.CreateBookmarkRequest
import work.socialhub.kmixi2web.api.request.CreatePostRequest
import work.socialhub.kmixi2web.api.request.DeleteBookmarkRequest
import work.socialhub.kmixi2web.api.request.DeletePostRequest
import work.socialhub.kmixi2web.api.request.DeleteRepostRequest
import work.socialhub.kmixi2web.api.request.GetPostRequest
import work.socialhub.kmixi2web.api.request.GetPostsRequest
import work.socialhub.kmixi2web.api.request.GetQuotePostsRequest
import work.socialhub.kmixi2web.api.request.GetRepliesRequest
import work.socialhub.kmixi2web.api.request.GetReplyAncestorsRequest
import work.socialhub.kmixi2web.api.request.GetThreadPostsRequest
import work.socialhub.kmixi2web.api.response.CreateBookmarkResponse
import work.socialhub.kmixi2web.api.response.CreatePostResponse
import work.socialhub.kmixi2web.api.response.DeleteBookmarkResponse
import work.socialhub.kmixi2web.api.response.DeletePostResponse
import work.socialhub.kmixi2web.api.response.DeleteRepostResponse
import work.socialhub.kmixi2web.api.response.GetPostResponse
import work.socialhub.kmixi2web.api.response.GetPostsResponse
import work.socialhub.kmixi2web.api.response.GetQuotePostsResponse
import work.socialhub.kmixi2web.api.response.GetRepliesResponse
import work.socialhub.kmixi2web.api.response.GetReplyAncestorsResponse
import work.socialhub.kmixi2web.api.response.GetTimelineResponse
import work.socialhub.kmixi2web.entity.share.Response
import kotlin.js.JsExport

@JsExport
interface PostResource {
    suspend fun getPost(request: GetPostRequest): Response<GetPostResponse>

    @JsExport.Ignore
    fun getPostBlocking(request: GetPostRequest): Response<GetPostResponse>

    suspend fun getPosts(request: GetPostsRequest): Response<GetPostsResponse>

    @JsExport.Ignore
    fun getPostsBlocking(request: GetPostsRequest): Response<GetPostsResponse>

    suspend fun getReplies(request: GetRepliesRequest): Response<GetRepliesResponse>

    @JsExport.Ignore
    fun getRepliesBlocking(request: GetRepliesRequest): Response<GetRepliesResponse>

    suspend fun getReplyAncestors(
        request: GetReplyAncestorsRequest,
    ): Response<GetReplyAncestorsResponse>

    @JsExport.Ignore
    fun getReplyAncestorsBlocking(
        request: GetReplyAncestorsRequest,
    ): Response<GetReplyAncestorsResponse>

    suspend fun getThreadPosts(
        request: GetThreadPostsRequest,
    ): Response<GetTimelineResponse>

    @JsExport.Ignore
    fun getThreadPostsBlocking(
        request: GetThreadPostsRequest,
    ): Response<GetTimelineResponse>

    suspend fun createPost(request: CreatePostRequest): Response<CreatePostResponse>

    @JsExport.Ignore
    fun createPostBlocking(request: CreatePostRequest): Response<CreatePostResponse>

    suspend fun deletePost(request: DeletePostRequest): Response<DeletePostResponse>

    @JsExport.Ignore
    fun deletePostBlocking(request: DeletePostRequest): Response<DeletePostResponse>

    suspend fun deleteRepost(
        request: DeleteRepostRequest,
    ): Response<DeleteRepostResponse>

    @JsExport.Ignore
    fun deleteRepostBlocking(
        request: DeleteRepostRequest,
    ): Response<DeleteRepostResponse>

    suspend fun getQuotePosts(
        request: GetQuotePostsRequest,
    ): Response<GetQuotePostsResponse>

    @JsExport.Ignore
    fun getQuotePostsBlocking(
        request: GetQuotePostsRequest,
    ): Response<GetQuotePostsResponse>

    suspend fun createBookmark(
        request: CreateBookmarkRequest,
    ): Response<CreateBookmarkResponse>

    @JsExport.Ignore
    fun createBookmarkBlocking(
        request: CreateBookmarkRequest,
    ): Response<CreateBookmarkResponse>

    suspend fun deleteBookmark(
        request: DeleteBookmarkRequest,
    ): Response<DeleteBookmarkResponse>

    @JsExport.Ignore
    fun deleteBookmarkBlocking(
        request: DeleteBookmarkRequest,
    ): Response<DeleteBookmarkResponse>
}
