package work.socialhub.kmixi2web

import work.socialhub.kmixi2web.api.request.GetCommunityRequest
import work.socialhub.kmixi2web.api.request.GetCommunityTimelineRequest
import work.socialhub.kmixi2web.api.request.GetParticipatingCommunitiesRequest
import work.socialhub.kmixi2web.api.request.JoinCommunityRequest
import work.socialhub.kmixi2web.api.response.GetParticipatingCommunitiesResponse
import work.socialhub.kmixi2web.api.response.GetParticipatingCommunityMembersResponse
import work.socialhub.kmixi2web.api.response.LeaveCommunityResponse
import work.socialhub.kmixi2web.entity.CommunityAccessLevel
import work.socialhub.kmixi2web.entity.CommunityMember
import work.socialhub.kmixi2web.entity.CommunityMemberStatus
import work.socialhub.kmixi2web.entity.CommunityType
import work.socialhub.kmixi2web.entity.CommunityVisibility
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CommunityWireTest {

    @Test
    fun participatingCommunitiesRequestEncodesOptionalFilters() {
        val encoded = wireProto.encodeToByteArray(
            GetParticipatingCommunitiesRequest.serializer(),
            GetParticipatingCommunitiesRequest(
                personaId = "p",
                type = CommunityType.EVENT,
                limit = 20,
                rejectArchived = true,
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x70,
                0x10, 0x01,
                0x18, 0x14,
                0x30, 0x01,
            ),
            encoded,
        )
    }

    @Test
    fun getCommunityRequestUsesCommunityIdField() {
        val encoded = wireProto.encodeToByteArray(
            GetCommunityRequest.serializer(),
            GetCommunityRequest(communityId = "c"),
        )

        assertContentEquals(
            bytes(0x0A, 0x01, 0x63),
            encoded,
        )
    }

    @Test
    fun communityTimelineRequestEncodesCursorsAndMediaOnly() {
        val encoded = wireProto.encodeToByteArray(
            GetCommunityTimelineRequest.serializer(),
            GetCommunityTimelineRequest(
                communityId = "c",
                untilCursorId = "u",
                limit = 5,
                mediaOnly = true,
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x63,
                0x12, 0x01, 0x75,
                0x20, 0x05,
                0x30, 0x01,
            ),
            encoded,
        )
    }

    @Test
    fun joinCommunityRequestEncodesBlockingCheckFlag() {
        val encoded = wireProto.encodeToByteArray(
            JoinCommunityRequest.serializer(),
            JoinCommunityRequest(
                communityId = "c",
                skipBlockingMemberCheck = true,
            ),
        )

        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x63,
                0x10, 0x01,
            ),
            encoded,
        )
    }

    @Test
    fun memberStatusEncodesByCodeNotOrdinal() {
        val encoded = wireProto.encodeToByteArray(
            CommunityMember.serializer(),
            CommunityMember(
                communityId = "c",
                status = CommunityMemberStatus.EXCLUDED,
            ),
        )

        // EXCLUDED is 3 on the wire even though it is the third enum entry.
        assertContentEquals(
            bytes(
                0x0A, 0x01, 0x63,
                0x18, 0x03,
            ),
            encoded,
        )
    }

    @Test
    fun participatingCommunitiesResponseDecodesCommunityFields() {
        val decoded = wireProto.decodeFromByteArray(
            GetParticipatingCommunitiesResponse.serializer(),
            bytes(
                0x0A, 0x0F,
                0x0A, 0x01, 0x63,
                0x12, 0x01, 0x6E,
                0x20, 0x01,
                0x40, 0x07,
                0x68, 0x01,
                0xA8, 0x01, 0x01,
                0x12, 0x01, 0x78,
            ),
        )

        val community = decoded.communities.single()
        assertEquals("c", community.communityId)
        assertEquals("n", community.name)
        assertEquals(CommunityAccessLevel.APPROVAL_REQUIRED, community.accessLevel)
        assertEquals(7L, community.countOfMembers)
        assertEquals(CommunityType.EVENT, community.type)
        assertEquals(CommunityVisibility.VISIBLE, community.visibility)
        assertEquals("x", decoded.nextCursor)
    }

    @Test
    fun participatingCommunityMembersResponseDecodesMembers() {
        val decoded = wireProto.decodeFromByteArray(
            GetParticipatingCommunityMembersResponse.serializer(),
            bytes(
                0x0A, 0x0F,
                0x0A, 0x01, 0x63,
                0x12, 0x03,
                0x0A, 0x01, 0x70,
                0x18, 0x03,
                0x20, 0x01,
                0x3A, 0x01, 0x70,
                0x12, 0x01, 0x79,
            ),
        )

        val member = decoded.members.single()
        assertEquals("c", member.communityId)
        assertEquals("p", member.persona?.personaId)
        assertEquals(CommunityMemberStatus.EXCLUDED, member.status)
        assertTrue(member.isAdmin)
        assertEquals("p", member.personaId)
        assertEquals("y", decoded.cursor)
    }

    @Test
    fun leaveCommunityResponseDecodesLeftChildIds() {
        val decoded = wireProto.decodeFromByteArray(
            LeaveCommunityResponse.serializer(),
            bytes(
                0x0A, 0x01, 0x61,
                0x0A, 0x01, 0x62,
            ),
        )

        assertContentEquals(listOf("a", "b"), decoded.leftChildCommunityIds)
    }
}
