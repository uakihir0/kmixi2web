package work.socialhub.kmixi2web

import kotlinx.serialization.protobuf.ProtoBuf
import work.socialhub.kmixi2web.api.request.CreatePostRequest
import work.socialhub.kmixi2web.api.request.GetPostRequest
import work.socialhub.kmixi2web.api.request.GetSubscribingFeedsRequest
import work.socialhub.kmixi2web.api.response.GetPostResponse
import work.socialhub.kmixi2web.entity.FeedSourceType
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ProtoWireTest {
    private val proto = ProtoBuf {
        encodeDefaults = false
    }

    @Test
    fun getPostRequestMatchesMercuryWireFormat() {
        val encoded = proto.encodeToByteArray(
            GetPostRequest.serializer(),
            GetPostRequest("abc"),
        )

        assertContentEquals(
            bytes(0x0A, 0x03, 0x61, 0x62, 0x63),
            encoded,
        )
    }

    @Test
    fun subscribingFeedRequestUsesObservedFieldNumbers() {
        val encoded = proto.encodeToByteArray(
            GetSubscribingFeedsRequest.serializer(),
            GetSubscribingFeedsRequest(
                limit = 50,
                feedSourceType = FeedSourceType.FOLLOWING,
            ),
        )

        assertContentEquals(
            bytes(0x10, 0x32, 0x28, 0x01),
            encoded,
        )
    }

    @Test
    fun createPostRequestMatchesObservedFields() {
        val encoded = proto.encodeToByteArray(
            CreatePostRequest.serializer(),
            CreatePostRequest(
                text = "hi",
                inReplyToPostId = "p1",
                mediaIds = listOf("m1", "m2"),
                isSensitive = true,
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x02, 0x68, 0x69,
                0x12, 0x02, 0x70, 0x31,
                0x22, 0x02, 0x6D, 0x31,
                0x22, 0x02, 0x6D, 0x32,
                0x30, 0x01,
            ),
            encoded,
        )
    }

    @Test
    fun postResponseIgnoresUnknownFields() {
        val decoded = proto.decodeFromByteArray(
            GetPostResponse.serializer(),
            bytes(
                0x0A, 0x09,
                0x0A, 0x01, 0x70,
                0x48, 0x03,
                0x62, 0x02, 0x68, 0x69,
                0xA0, 0x06, 0x07,
            ),
        )

        assertEquals("p", decoded.post?.postId)
        assertEquals(3, decoded.post?.likesCount)
        assertEquals("hi", decoded.post?.text)
    }

    private fun bytes(vararg values: Int): ByteArray {
        return ByteArray(values.size) { index -> values[index].toByte() }
    }
}
