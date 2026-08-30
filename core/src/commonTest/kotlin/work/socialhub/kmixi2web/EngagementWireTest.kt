package work.socialhub.kmixi2web

import work.socialhub.kmixi2web.api.request.CreateBookmarkRequest
import work.socialhub.kmixi2web.api.request.DeleteRepostRequest
import work.socialhub.kmixi2web.api.request.GetQuotePostsRequest
import work.socialhub.kmixi2web.api.request.GetReactionPostsRequest
import work.socialhub.kmixi2web.api.request.GetRepostingPersonasRequest
import work.socialhub.kmixi2web.api.response.CreateBookmarkResponse
import work.socialhub.kmixi2web.api.response.DeleteRepostResponse
import work.socialhub.kmixi2web.api.response.GetReactionPostsResponse
import work.socialhub.kmixi2web.api.response.GetRepostingPersonasResponse
import work.socialhub.kmixi2web.entity.PostReactionType
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class EngagementWireTest {

    @Test
    fun bookmarkAndRepostRequestsUseSingleTargetField() {
        val bookmark = wireProto.encodeToByteArray(
            CreateBookmarkRequest.serializer(),
            CreateBookmarkRequest(postId = "p"),
        )
        val repost = wireProto.encodeToByteArray(
            DeleteRepostRequest.serializer(),
            DeleteRepostRequest(referencePostId = "p"),
        )

        val expected = bytes(0x0A, 0x01, 0x70)
        assertContentEquals(expected, bookmark)
        assertContentEquals(expected, repost)
    }

    @Test
    fun pagedEngagementRequestsUseIdLimitCursorFields() {
        val quotes = wireProto.encodeToByteArray(
            GetQuotePostsRequest.serializer(),
            GetQuotePostsRequest(postId = "p", limit = 20, cursor = "c"),
        )
        val reposters = wireProto.encodeToByteArray(
            GetRepostingPersonasRequest.serializer(),
            GetRepostingPersonasRequest(postId = "p", limit = 20, cursor = "c"),
        )

        val expected = bytes(
            0x0A, 0x01, 0x70,
            0x10, 0x14,
            0x1A, 0x01, 0x63,
        )
        assertContentEquals(expected, quotes)
        assertContentEquals(expected, reposters)
    }

    @Test
    fun reactionPostsRequestEncodesNonContiguousReactionType() {
        val likes = wireProto.encodeToByteArray(
            GetReactionPostsRequest.serializer(),
            GetReactionPostsRequest(
                reactionType = PostReactionType.LIKE,
                limit = 20,
            ),
        )
        val bookmarks = wireProto.encodeToByteArray(
            GetReactionPostsRequest.serializer(),
            GetReactionPostsRequest(
                reactionType = PostReactionType.BOOKMARK,
            ),
        )

        assertContentEquals(
            bytes(
                0x08, 0xC8, 0x01,
                0x10, 0x14,
            ),
            likes,
        )
        assertContentEquals(
            bytes(0x08, 0xC9, 0x01),
            bookmarks,
        )
    }

    @Test
    fun bookmarkResponseDecodesUpdatedPost() {
        val decoded = wireProto.decodeFromByteArray(
            CreateBookmarkResponse.serializer(),
            bytes(
                0x0A, 0x03,
                0x0A, 0x01, 0x70,
            ),
        )

        assertEquals("p", decoded.post?.postId)
    }

    @Test
    fun deleteRepostResponseDecodesDeletedIdAndReferencePost() {
        val decoded = wireProto.decodeFromByteArray(
            DeleteRepostResponse.serializer(),
            bytes(
                0x0A, 0x01, 0x64,
                0x12, 0x03,
                0x0A, 0x01, 0x70,
            ),
        )

        assertEquals("d", decoded.deletedPostId)
        assertEquals("p", decoded.referencePost?.postId)
    }

    @Test
    fun pagedEngagementResponsesDecodeCursorAndHasNext() {
        val posts = wireProto.decodeFromByteArray(
            GetReactionPostsResponse.serializer(),
            bytes(
                0x0A, 0x03,
                0x0A, 0x01, 0x70,
                0x12, 0x01, 0x63,
                0x18, 0x01,
            ),
        )
        val personas = wireProto.decodeFromByteArray(
            GetRepostingPersonasResponse.serializer(),
            bytes(
                0x0A, 0x03,
                0x0A, 0x01, 0x70,
                0x12, 0x01, 0x63,
                0x18, 0x01,
            ),
        )

        assertEquals("p", posts.posts.single().postId)
        assertEquals("c", posts.nextCursor)
        assertEquals(true, posts.hasNext)
        assertEquals("p", personas.personas.single().personaId)
        assertEquals("c", personas.nextCursor)
        assertEquals(true, personas.hasNext)
    }
}
