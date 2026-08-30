package work.socialhub.kmixi2web

import work.socialhub.kmixi2web.api.request.GetMediaRequest
import work.socialhub.kmixi2web.api.request.PrepareMediaUploadingRequest
import work.socialhub.kmixi2web.api.response.GetMediaResponse
import work.socialhub.kmixi2web.api.response.PrepareMediaUploadingResponse
import work.socialhub.kmixi2web.entity.MediaCategory
import work.socialhub.kmixi2web.entity.MediaStatus
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class MediaWireTest {

    @Test
    fun prepareRequestEncodesMimeTypeSizeAndCategory() {
        val encoded = wireProto.encodeToByteArray(
            PrepareMediaUploadingRequest.serializer(),
            PrepareMediaUploadingRequest(
                mimeType = "i",
                dataSize = 100,
                category = MediaCategory.POST_IMAGE,
                description = "d",
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x69,
                0x10, 0x64,
                0x18, 0x02,
                0x2A, 0x01, 0x64,
            ),
            encoded,
        )
    }

    @Test
    fun getMediaRequestUsesMediaIdField() {
        val encoded = wireProto.encodeToByteArray(
            GetMediaRequest.serializer(),
            GetMediaRequest(mediaId = "m"),
        )

        assertContentEquals(
            bytes(0x0A, 0x01, 0x6D),
            encoded,
        )
    }

    @Test
    fun prepareResponseDecodesMediaIdAndUploadTarget() {
        val decoded = wireProto.decodeFromByteArray(
            PrepareMediaUploadingResponse.serializer(),
            bytes(
                0x0A, 0x01, 0x6D,
                0x12, 0x11,
                0x0A, 0x01, 0x75,
                0x12, 0x03, 0x50, 0x55, 0x54,
                0x1A, 0x07,
                0x0A, 0x01, 0x6B,
                0x12, 0x02, 0x76, 0x31,
            ),
        )

        assertEquals("m", decoded.mediaId)
        assertEquals("u", decoded.request?.url)
        assertEquals("PUT", decoded.request?.method)
        assertEquals("k", decoded.request?.headers?.single()?.key)
        assertEquals("v1", decoded.request?.headers?.single()?.value)
    }

    @Test
    fun getMediaResponseDecodesCategoryAndStatus() {
        val decoded = wireProto.decodeFromByteArray(
            GetMediaResponse.serializer(),
            bytes(
                0x0A, 0x0F,
                0x0A, 0x01, 0x6D,
                0x18, 0x02,
                0x20, 0x03,
                0x32, 0x06,
                0x0A, 0x01, 0x6C,
                0x12, 0x01, 0x69,
            ),
        )

        val media = decoded.media
        assertEquals("m", media?.mediaId)
        assertEquals(MediaCategory.POST_IMAGE, media?.category)
        assertEquals(MediaStatus.SUCCESS, media?.status)
        assertEquals("l", media?.postImage?.largeImageUrl)
        assertEquals("i", media?.postImage?.largeImageMimeType)
    }

    @Test
    fun getMediaResponseDecodesProcessingStatus() {
        val decoded = wireProto.decodeFromByteArray(
            GetMediaResponse.serializer(),
            bytes(
                0x0A, 0x05,
                0x0A, 0x01, 0x6D,
                0x20, 0x02,
            ),
        )

        assertEquals(MediaStatus.IN_PROGRESS, decoded.media?.status)
    }
}
