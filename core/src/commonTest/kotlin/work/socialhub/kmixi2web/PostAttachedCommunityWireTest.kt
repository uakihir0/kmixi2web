package work.socialhub.kmixi2web

import work.socialhub.kmixi2web.api.response.GetPostResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PostAttachedCommunityWireTest {

    @Test
    fun postDecodesAttachedCommunityFromField26() {
        val decoded = wireProto.decodeFromByteArray(
            GetPostResponse.serializer(),
            bytes(
                0x0A, 0x0E,
                0x0A, 0x01, 0x70,
                0xD2, 0x01, 0x08,
                0x0A, 0x01, 0x63,
                0x12, 0x01, 0x6E,
                0x40, 0x04,
            ),
        )

        val post = decoded.post
        assertEquals("p", post?.postId)
        assertNull(post?.community)
        assertEquals("c", post?.attachedCommunity?.communityId)
        assertEquals("n", post?.attachedCommunity?.name)
        assertEquals(4L, post?.attachedCommunity?.countOfMembers)
    }

    @Test
    fun postWithoutAttachedCommunityHasNoAttachedCommunity() {
        val decoded = wireProto.decodeFromByteArray(
            GetPostResponse.serializer(),
            bytes(
                0x0A, 0x03,
                0x0A, 0x01, 0x70,
            ),
        )

        assertEquals("p", decoded.post?.postId)
        assertNull(decoded.post?.attachedCommunity)
    }
}
