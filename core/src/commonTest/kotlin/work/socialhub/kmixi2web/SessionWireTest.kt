package work.socialhub.kmixi2web

import work.socialhub.kmixi2web.api.request.GetProfileByNameRequest
import work.socialhub.kmixi2web.api.request.GetProfileRequest
import work.socialhub.kmixi2web.api.request.SwitchPersonaRequest
import work.socialhub.kmixi2web.api.request.UpdateProfileRequest
import work.socialhub.kmixi2web.api.response.GetProfileResponse
import work.socialhub.kmixi2web.api.response.SessionResponse
import work.socialhub.kmixi2web.entity.StatusIcon
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class SessionWireTest {

    @Test
    fun switchPersonaRequestUsesPersonaIdField() {
        val encoded = wireProto.encodeToByteArray(
            SwitchPersonaRequest.serializer(),
            SwitchPersonaRequest(personaId = "p"),
        )

        assertContentEquals(
            bytes(0x0A, 0x01, 0x70),
            encoded,
        )
    }

    @Test
    fun profileRequestsUseSingleLookupField() {
        val byId = wireProto.encodeToByteArray(
            GetProfileRequest.serializer(),
            GetProfileRequest(personaId = "p"),
        )
        val byName = wireProto.encodeToByteArray(
            GetProfileByNameRequest.serializer(),
            GetProfileByNameRequest(name = "p"),
        )

        val expected = bytes(0x0A, 0x01, 0x70)
        assertContentEquals(expected, byId)
        assertContentEquals(expected, byName)
    }

    @Test
    fun updateProfileRequestMatchesObservedFields() {
        val encoded = wireProto.encodeToByteArray(
            UpdateProfileRequest.serializer(),
            UpdateProfileRequest(
                displayName = "n",
                profileText = "t",
                statusIcon = StatusIcon(type = 1, icon = "e"),
                statusText = "s",
                link = "l",
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x6E,
                0x12, 0x01, 0x74,
                0x1A, 0x05,
                0x18, 0x01,
                0x22, 0x01, 0x65,
                0x22, 0x01, 0x73,
                0x2A, 0x01, 0x6C,
            ),
            encoded,
        )
    }

    @Test
    fun sessionResponseDecodesManagedPersonasAndActivePersona() {
        val decoded = wireProto.decodeFromByteArray(
            SessionResponse.serializer(),
            bytes(
                0x0A, 0x10,
                0x0A, 0x0C,
                0x0A, 0x03,
                0x0A, 0x01, 0x70,
                0x10, 0x02,
                0x18, 0x03,
                0x22, 0x01, 0x62,
                0x10, 0x01,
                0x12, 0x01, 0x70,
                0x18, 0x01,
            ),
        )

        val managed = decoded.sessionManagedPersonas.single()
        assertEquals("p", managed.profile?.persona?.personaId)
        assertEquals(2, managed.profile?.followingCount)
        assertEquals(3, managed.profile?.followedCount)
        assertEquals("b", managed.profile?.text)
        assertEquals(true, managed.isSharedPersona)
        assertEquals("p", decoded.activePersonaId)
        assertEquals(true, decoded.isAccountFrozen)
    }

    @Test
    fun profileResponseDecodesCountsAndModerationState() {
        val decoded = wireProto.decodeFromByteArray(
            GetProfileResponse.serializer(),
            bytes(
                0x0A, 0x27,
                0x0A, 0x03,
                0x0A, 0x01, 0x70,
                0x10, 0x01,
                0x18, 0x02,
                0x22, 0x01, 0x62,
                0x2A, 0x01, 0x69,
                0x32, 0x01, 0x6C,
                0x3A, 0x04,
                0x08, 0x01,
                0x20, 0x01,
                0x40, 0x01,
                0x50, 0x01,
                0x58, 0x01,
                0x6A, 0x07,
                0x08, 0x01,
                0x12, 0x01, 0x75,
                0x18, 0x01,
            ),
        )

        val profile = decoded.profile
        assertEquals("p", profile?.persona?.personaId)
        assertEquals(1, profile?.followingCount)
        assertEquals(2, profile?.followedCount)
        assertEquals("b", profile?.text)
        assertEquals("i", profile?.profileImageUrl)
        assertEquals("l", profile?.link)
        assertEquals(true, profile?.personaConnectivity?.following)
        assertEquals(true, profile?.personaConnectivity?.followed)
        assertEquals(true, profile?.isMuted)
        assertEquals(true, profile?.isBlocking)
        assertEquals(true, profile?.isBlocked)
        assertEquals(1, profile?.socialMedia?.single()?.socialMediaType)
        assertEquals("u", profile?.socialMedia?.single()?.username)
        assertEquals(true, profile?.socialMedia?.single()?.verified)
    }
}
