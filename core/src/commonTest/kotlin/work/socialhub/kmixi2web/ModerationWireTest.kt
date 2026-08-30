package work.socialhub.kmixi2web

import work.socialhub.kmixi2web.api.request.MakePersonaBlockRequest
import work.socialhub.kmixi2web.api.request.MakePersonaMuteRequest
import work.socialhub.kmixi2web.api.request.ReportPersonaRequest
import work.socialhub.kmixi2web.api.request.ReportPostRequest
import work.socialhub.kmixi2web.api.response.GetBlockPersonasResponse
import work.socialhub.kmixi2web.api.response.MakePersonaBlockResponse
import work.socialhub.kmixi2web.api.response.MakePersonaMuteResponse
import work.socialhub.kmixi2web.entity.ReportReasonType
import work.socialhub.kmixi2web.entity.ReportRightInfringementTarget
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ModerationWireTest {

    @Test
    fun personaModerationRequestsUsePersonaIdField() {
        val block = wireProto.encodeToByteArray(
            MakePersonaBlockRequest.serializer(),
            MakePersonaBlockRequest(personaId = "p"),
        )
        val mute = wireProto.encodeToByteArray(
            MakePersonaMuteRequest.serializer(),
            MakePersonaMuteRequest(personaId = "p"),
        )

        val expected = bytes(0x0A, 0x01, 0x70)
        assertContentEquals(expected, block)
        assertContentEquals(expected, mute)
    }

    @Test
    fun reportPostRequestEncodesReasonAsEnumNumber() {
        val encoded = wireProto.encodeToByteArray(
            ReportPostRequest.serializer(),
            ReportPostRequest(
                postId = "p",
                reasonType = ReportReasonType.SPAM,
                reasonContent = "c",
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x70,
                0x10, 0x01,
                0x1A, 0x01, 0x63,
            ),
            encoded,
        )
    }

    @Test
    fun reportPersonaRequestEncodesRightInfringementTarget() {
        val encoded = wireProto.encodeToByteArray(
            ReportPersonaRequest.serializer(),
            ReportPersonaRequest(
                personaId = "p",
                reasonType = ReportReasonType.RIGHT_INFRINGEMENT,
                reasonContent = "c",
                rightInfringementTarget = ReportRightInfringementTarget.OTHERS,
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x70,
                0x10, 0x07,
                0x1A, 0x01, 0x63,
                0x20, 0x02,
            ),
            encoded,
        )
    }

    @Test
    fun reportRequestOmitsUnsetRightInfringementTarget() {
        val encoded = wireProto.encodeToByteArray(
            ReportPostRequest.serializer(),
            ReportPostRequest(
                postId = "p",
                reasonType = ReportReasonType.OTHER,
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x70,
                0x10, 0x06,
            ),
            encoded,
        )
    }

    @Test
    fun blockResponseDecodesProfileAndMuteResponseDecodesPersona() {
        val block = wireProto.decodeFromByteArray(
            MakePersonaBlockResponse.serializer(),
            bytes(
                0x0A, 0x07,
                0x0A, 0x03,
                0x0A, 0x01, 0x70,
                0x50, 0x01,
            ),
        )
        val mute = wireProto.decodeFromByteArray(
            MakePersonaMuteResponse.serializer(),
            bytes(
                0x0A, 0x03,
                0x0A, 0x01, 0x70,
            ),
        )

        assertEquals("p", block.profile?.persona?.personaId)
        assertEquals(true, block.profile?.isBlocking)
        assertEquals("p", mute.persona?.personaId)
    }

    @Test
    fun blockPersonasResponseDecodesRepeatedIds() {
        val decoded = wireProto.decodeFromByteArray(
            GetBlockPersonasResponse.serializer(),
            bytes(
                0x0A, 0x01, 0x61,
                0x0A, 0x01, 0x62,
            ),
        )

        assertContentEquals(listOf("a", "b"), decoded.personaIds)
    }
}
