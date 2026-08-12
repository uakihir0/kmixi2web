package work.socialhub.kmixi2web.internal.api

import work.socialhub.kmixi2web.api.PostResource
import work.socialhub.kmixi2web.api.request.CreatePostRequest
import work.socialhub.kmixi2web.api.request.DeletePostRequest
import work.socialhub.kmixi2web.api.request.GetPostRequest
import work.socialhub.kmixi2web.api.request.GetPostsRequest
import work.socialhub.kmixi2web.api.request.GetRepliesRequest
import work.socialhub.kmixi2web.api.request.GetReplyAncestorsRequest
import work.socialhub.kmixi2web.api.request.GetThreadPostsRequest
import work.socialhub.kmixi2web.api.response.CreatePostResponse
import work.socialhub.kmixi2web.api.response.DeletePostResponse
import work.socialhub.kmixi2web.api.response.GetPostResponse
import work.socialhub.kmixi2web.api.response.GetPostsResponse
import work.socialhub.kmixi2web.api.response.GetRepliesResponse
import work.socialhub.kmixi2web.api.response.GetReplyAncestorsResponse
import work.socialhub.kmixi2web.api.response.GetTimelineResponse
import work.socialhub.kmixi2web.entity.share.Response
import work.socialhub.kmixi2web.internal.MercuryClient
import work.socialhub.kmixi2web.util.toBlocking

internal class PostResourceImpl(
    private val client: MercuryClient,
) : PostResource {
    override suspend fun getPost(
        request: GetPostRequest,
    ): Response<GetPostResponse> {
        return client.call(
            "GetPost",
            request,
            GetPostRequest.serializer(),
            GetPostResponse.serializer(),
        )
    }

    override fun getPostBlocking(
        request: GetPostRequest,
    ) = toBlocking { getPost(request) }

    override suspend fun getPosts(
        request: GetPostsRequest,
    ): Response<GetPostsResponse> {
        return client.call(
            "GetPosts",
            request,
            GetPostsRequest.serializer(),
            GetPostsResponse.serializer(),
        )
    }

    override fun getPostsBlocking(
        request: GetPostsRequest,
    ) = toBlocking { getPosts(request) }

    override suspend fun getReplies(
        request: GetRepliesRequest,
    ): Response<GetRepliesResponse> {
        return client.call(
            "GetReplies",
            request,
            GetRepliesRequest.serializer(),
            GetRepliesResponse.serializer(),
        )
    }

    override fun getRepliesBlocking(
        request: GetRepliesRequest,
    ) = toBlocking { getReplies(request) }

    override suspend fun getReplyAncestors(
        request: GetReplyAncestorsRequest,
    ): Response<GetReplyAncestorsResponse> {
        return client.call(
            "GetReplyAncestors",
            request,
            GetReplyAncestorsRequest.serializer(),
            GetReplyAncestorsResponse.serializer(),
        )
    }

    override fun getReplyAncestorsBlocking(
        request: GetReplyAncestorsRequest,
    ) = toBlocking { getReplyAncestors(request) }

    override suspend fun getThreadPosts(
        request: GetThreadPostsRequest,
    ): Response<GetTimelineResponse> {
        return client.call(
            "GetThreadPosts",
            request,
            GetThreadPostsRequest.serializer(),
            GetTimelineResponse.serializer(),
        )
    }

    override fun getThreadPostsBlocking(
        request: GetThreadPostsRequest,
    ) = toBlocking { getThreadPosts(request) }

    override suspend fun createPost(
        request: CreatePostRequest,
    ): Response<CreatePostResponse> {
        return client.call(
            "CreatePost",
            request,
            CreatePostRequest.serializer(),
            CreatePostResponse.serializer(),
        )
    }

    override fun createPostBlocking(
        request: CreatePostRequest,
    ) = toBlocking { createPost(request) }

    override suspend fun deletePost(
        request: DeletePostRequest,
    ): Response<DeletePostResponse> {
        return client.call(
            "DeletePost",
            request,
            DeletePostRequest.serializer(),
            DeletePostResponse.serializer(),
        )
    }

    override fun deletePostBlocking(
        request: DeletePostRequest,
    ) = toBlocking { deletePost(request) }
}
