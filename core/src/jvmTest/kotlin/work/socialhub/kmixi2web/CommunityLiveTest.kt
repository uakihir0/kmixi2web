package work.socialhub.kmixi2web

import kotlinx.coroutines.runBlocking
import work.socialhub.kmixi2web.api.request.GetCommunityRequest
import work.socialhub.kmixi2web.api.request.GetCommunityTimelineRequest
import work.socialhub.kmixi2web.api.request.GetParticipatingCommunitiesRequest
import work.socialhub.kmixi2web.api.request.GetParticipatingCommunityMembersRequest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class CommunityLiveTest {
    @Test
    fun readCommunitiesTimelineAndMembers() = runBlocking {
        LiveTestSupport.requireMode("community-read")
        val client = LiveTestSupport.client()

        val communities = client.community().getParticipatingCommunities(
            GetParticipatingCommunitiesRequest(
                limit = PAGE_SIZE,
                rejectArchived = true,
            )
        )
        assertEquals(200, communities.status)
        communities.data.communities.forEach {
            assertTrue(it.communityId.isNotBlank())
            assertTrue(it.name.isNotBlank())
            assertTrue(!it.isArchived)
        }

        val first = communities.data.communities.firstOrNull()
        if (first == null) {
            println("The active persona participates in no community; nothing to read.")
            return@runBlocking
        }

        val community = client.community().getCommunity(
            GetCommunityRequest(first.communityId)
        )
        assertEquals(200, community.status)
        val detail = assertNotNull(community.data.community)
        assertEquals(first.communityId, detail.communityId)
        assertEquals(first.name, detail.name)
        assertTrue(detail.countOfMembers >= 1)

        val timeline = client.community().getCommunityTimeline(
            GetCommunityTimelineRequest(
                communityId = first.communityId,
                limit = PAGE_SIZE,
            )
        )
        assertEquals(200, timeline.status)
        timeline.data.posts.forEach {
            assertTrue(it.postId.isNotBlank())
        }

        val members = client.community().getParticipatingCommunityMembers(
            GetParticipatingCommunityMembersRequest(
                communityId = first.communityId,
                limit = PAGE_SIZE,
            )
        )
        assertEquals(200, members.status)
        members.data.members.forEach {
            assertTrue(it.personaId.isNotBlank())
            assertEquals(first.communityId, it.communityId)
        }
        assertTrue(members.data.members.isNotEmpty(), "Expected at least one member")

        println(
            "Read ${communities.data.communities.size} community(ies); " +
                "${detail.name} has ${detail.countOfMembers} member(s), " +
                "${timeline.data.posts.size} post(s) on the first page."
        )
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
