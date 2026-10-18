package work.socialhub.kmixi2web

import work.socialhub.kmixi2web.api.response.GetPostResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PostImportedDateWireTest {

    @Test
    fun postDecodesImportedDateAndSource() {
        // Observed on a post imported from an X archive:
        // field 38 = google.type.Date { year: 2023, month: 5, day: 19 }, field 42 = 1 (X_ARCHIVE)
        val decoded = wireProto.decodeFromByteArray(
            GetPostResponse.serializer(),
            bytes(
                0x0A, 0x10,
                0x0A, 0x01, 0x70,
                0xB2, 0x02, 0x07, 0x08, 0xE7, 0x0F, 0x10, 0x05, 0x18, 0x13,
                0xD0, 0x02, 0x01,
            ),
        )

        val post = decoded.post
        assertEquals("p", post?.postId)
        assertEquals(2023, post?.date?.year)
        assertEquals(5, post?.date?.month)
        assertEquals(19, post?.date?.day)
        assertEquals(1, post?.importSource)
    }

    @Test
    fun postWithoutImportFieldsHasNoDate() {
        val decoded = wireProto.decodeFromByteArray(
            GetPostResponse.serializer(),
            bytes(
                0x0A, 0x03,
                0x0A, 0x01, 0x70,
            ),
        )

        val post = decoded.post
        assertEquals("p", post?.postId)
        assertNull(post?.date)
        assertEquals(0, post?.importSource)
    }

    @Test
    fun postDecodesUnknownImportSourceAsRawValue() {
        val decoded = wireProto.decodeFromByteArray(
            GetPostResponse.serializer(),
            bytes(
                0x0A, 0x06,
                0x0A, 0x01, 0x70,
                0xD0, 0x02, 0x02,
            ),
        )

        assertEquals("p", decoded.post?.postId)
        assertEquals(2, decoded.post?.importSource)
    }
}
